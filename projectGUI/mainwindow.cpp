#include "mainwindow.h"
#include <QPushButton>
#include <QLineEdit>
#include <QWebEngineView>
#include <QUrl>
#include <QVBoxLayout>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent)
{
    // Set up the main layout and widgets
    QWidget *centralWidget = new QWidget(this);
    QVBoxLayout *layout = new QVBoxLayout(centralWidget);

    // Create the video URL input field and buttons
    videoUrlLineEdit = new QLineEdit(this);
    videoUrlLineEdit->setPlaceholderText("Enter YouTube Video URL");
    layout->addWidget(videoUrlLineEdit);

    // Create buttons
    pauseButton = new QPushButton("Pause", this);
    skipButton = new QPushButton("Skip", this);
    layout->addWidget(pauseButton);
    layout->addWidget(skipButton);

    // Create the QWebEngineView to show the YouTube video
    webView = new QWebEngineView(this);
    layout->addWidget(webView);

    setCentralWidget(centralWidget);

    // Connect buttons to their slots
    connect(pauseButton, &QPushButton::clicked, this, &MainWindow::onPauseClicked);
    connect(skipButton, &QPushButton::clicked, this, &MainWindow::onSkipClicked);

    // Show the window
    setWindowTitle("YouTube Video Control");
    resize(800, 600);
}

MainWindow::~MainWindow() {}

void MainWindow::onPlayClicked()
{
    QString videoUrl = videoUrlLineEdit->text();
    if (videoUrl.isEmpty()) {
        return;
    }

    // Create the iframe embed URL for YouTube
    QString embedUrl = "https://www.youtube.com/embed/" + videoUrl.split("v=")[1] + "?enablejsapi=1";

    // Load the YouTube video into the QWebEngineView
    webView->setUrl(QUrl(embedUrl));
}

void MainWindow::onPauseClicked()
{
    // Execute JavaScript in the browser to pause the video
    webView->page()->runJavaScript("player.pauseVideo();");
}

void MainWindow::onSkipClicked()
{
    // Execute JavaScript to skip to the next video
    webView->page()->runJavaScript("player.nextVideo();");
}
