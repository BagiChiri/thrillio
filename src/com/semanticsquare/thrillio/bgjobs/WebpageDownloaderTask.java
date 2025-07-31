package com.semanticsquare.thrillio.bgjobs;

import com.semanticsquare.thrillio.dao.BookmarkDao;
import com.semanticsquare.thrillio.entities.Weblink;
import com.semanticsquare.thrillio.util.HttpConnect;
import com.semanticsquare.thrillio.util.IOUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebpageDownloaderTask implements Runnable {
    private static final long TIME_FRAME = 3000000000L; //3 seconds
    private static BookmarkDao dao = new BookmarkDao();
    ExecutorService executor = Executors.newFixedThreadPool(5);
    private boolean downloadAll = false;

    public WebpageDownloaderTask(boolean downloadAll) {
        this.downloadAll = downloadAll;
    }

    private List<Weblink> getWeblinks() {
        List<Weblink> weblinks = null;

        if (downloadAll) {
            weblinks = dao.getAllWeblinks();
            downloadAll = false;
        } else {
            weblinks = dao.getWeblinks(Weblink.DownloadStatus.NOT_ATTEMPTED);
        }
        return weblinks;
    }

    private void download(List<Weblink> weblinks) {
        List<Downloader<Weblink>> tasks = getTasks(weblinks);
        List<Future<Weblink>> futures = new ArrayList<>();

        try {
            futures = executor.invokeAll(tasks, TIME_FRAME, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<Weblink> future :
                futures) {
            try {
                if (! (future.isCancelled()) ) {
                    Weblink weblink = future.get();
                    String webPage = weblink.getHtmlPage();
                    if (webPage != null) {
                        IOUtil.write(webPage, weblink.getId());
                        weblink.setDownloadStatus(Weblink.DownloadStatus.SUCCESS);
                        System.out.println("Download Success: " + weblink.getUrl());
                    } else {
                        System.out.println("WebPage not Downloaded:) " + weblink.getUrl());
                    }
                } else {
                    System.out.println("\nTask is cancelled:) " + Thread.currentThread());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    private List<Downloader<Weblink>> getTasks(List<Weblink> weblinks) {
        List<Downloader<Weblink>> tasks = new ArrayList<>();
        for (Weblink weblink :
                weblinks) {
            tasks.add(new Downloader<Weblink>(weblink));
        }
        return tasks;
    }

    @Override
    public void run() {
        while (!(Thread.currentThread().isInterrupted())) {
            //Get weblinks
            List<Weblink> weblinks = getWeblinks();
            //Download concurrently
            if (weblinks.size() > 0) {
                download(weblinks);
            } else {
                System.out.println("No new weblinks to download:)");
            }
            //Wait
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    private static class Downloader<T extends Weblink> implements Callable<T> {
        private T weblink;

        private Downloader(T weblink) {
            this.weblink = weblink;
        }

        public T call() {
            try {
                if (!(weblink.getUrl().endsWith(".pdf"))) {
                    weblink.setDownloadStatus(Weblink.DownloadStatus.FAILED);

                    String htmlPage = HttpConnect.download(weblink.getUrl());
                    weblink.setHtmlPage(htmlPage);
                } else {
                    weblink.setDownloadStatus(Weblink.DownloadStatus.NOT_ELIGIBLE);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return weblink;
        }
    }


}
