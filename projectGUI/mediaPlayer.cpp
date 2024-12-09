#include "mediaPlayer.h"
#include <QVBoxLayout>
#include <QPushButton>

MediaPlayer::MediaPlayer(QWidget *parent) : QWidget(parent) {
    setupUI();
}

MediaPlayer::~MediaPlayer() {}

void MediaPlayer::setupUI() {
    QVBoxLayout *layout = new QVBoxLayout(this);
    QPushButton *playButton = new QPushButton("Play", this);
    layout->addWidget(playButton);
    setLayout(layout);
}
