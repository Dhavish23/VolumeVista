#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QPushButton>
#include <QLineEdit>
#include <QWebEngineView>
#include <QUrl>

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void onPlayClicked();
    void onPauseClicked();
    void onSkipClicked();

private:
    QWebEngineView *webView;
    QLineEdit *videoUrlLineEdit;
    QPushButton *pauseButton;
    QPushButton *skipButton;
};

#endif // MAINWINDOW_H
