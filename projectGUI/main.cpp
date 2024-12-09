#include <QApplication>
#include <QWidget>
#include <QPushButton>
#include <QMediaPlayer>
#include <QVBoxLayout>

class MediaPlayer : public QWidget {
public:
    MediaPlayer(QWidget *parent = nullptr) : QWidget(parent) {
        // Create a media player object
        player = new QMediaPlayer(this);

        // Create buttons
        QPushButton *pauseButton = new QPushButton("Pause", this);
        QPushButton *skipButton = new QPushButton("Skip", this);

        // Connect buttons to their respective slots
        connect(pauseButton, &QPushButton::clicked, this, &MediaPlayer::pauseMedia);
        connect(skipButton, &QPushButton::clicked, this, &MediaPlayer::skipMedia);

        // Set layout
        QVBoxLayout *layout = new QVBoxLayout(this);
        layout->addWidget(pauseButton);
        layout->addWidget(skipButton);
        setLayout(layout);

        // Load media file (example)
        player->setMedia(QUrl::fromLocalFile("path/to/your/media.mp3"));
        player->play();
    }

private slots:
    void pauseMedia() {
        player->pause();
    }

    void skipMedia() {
        // Logic to skip to the next track or a specific time
        player->setPosition(player->position() + 10000); // skips 10 seconds
    }

private:
    QMediaPlayer *player;
};

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);
    MediaPlayer mediaPlayer;
    mediaPlayer.show();
    return app.exec();
}
