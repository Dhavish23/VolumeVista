cmake_minimum_required(VERSION 3.29)
project(Dhavish23_VolumeVista_1stGUI_projectGUI)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)
include_directories(build/Desktop_Qt_6_8_1_MinGW_64_bit-Debug)
include_directories(build/Desktop_Qt_6_8_1_MinGW_64_bit-Debug/debug)

add_executable(Dhavish23_VolumeVista_1stGUI_projectGUI
    build/Desktop_Qt_6_8_1_MinGW_64_bit-Debug/debug/moc_mainwindow.cpp
    build/Desktop_Qt_6_8_1_MinGW_64_bit-Debug/debug/moc_mediaPlayer.cpp
    build/Desktop_Qt_6_8_1_MinGW_64_bit-Debug/debug/moc_predefs.h
    build/Desktop_Qt_6_8_1_MinGW_64_bit-Debug/ui_mainwindow.h
    main.cpp
    mainwindow.cpp
    mainwindow.h
    mainwindow.ui
    mediaPlayer.cpp
    mediaPlayer.h
        test.cpp
        test.h
        test.ui)
