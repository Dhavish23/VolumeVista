#ifndef MEDIAPLAYER_H
#define MEDIAPLAYER_H

#include <QWidget>

class MediaPlayer : public QWidget {
    Q_OBJECT

public:
    MediaPlayer(QWidget *parent = nullptr);
    ~MediaPlayer();

private:
    void setupUI(); // Function to setup the UI components
};

#endif // MEDIAPLAYER_H
