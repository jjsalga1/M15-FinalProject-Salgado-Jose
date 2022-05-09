package com.company.ascii;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Video {
    public static void printFrame(String framePath) throws FileNotFoundException {
        Scanner fileIn = new Scanner(new File(framePath));
        while (fileIn.hasNextLine()) {
            System.out.println(fileIn.nextLine());
        }
    }

    public static void playIntroSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException{
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File("C:\\Users\\salga\\Desktop\\NetflixIntro\\netflix-audio.wav"));
        DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(ais);

        clip.start();
        Thread.sleep(4000); // Duration should match length of audio file.
        clip.close();
    }

    public static void printNetflixIntro() throws FileNotFoundException, InterruptedException {
        String pathname = "C:\\Users\\salga\\Desktop\\NetflixIntro\\out%03d.png_ascii_image.txt";
        Thread.sleep(500);
        for (int i = 1; i <= 120; i++) {
            String framePath = String.format(pathname, i);
            printFrame(framePath);
            Thread.sleep(27);
        }
    }

    public static void netflixIntroThreads() {
        new Thread(() -> {
            try {
                playIntroSound();
            } catch (UnsupportedAudioFileException | IOException | InterruptedException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                printNetflixIntro();
            } catch (FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playNFMaySound() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException{
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File("C:\\Users\\salga\\Desktop\\NetflixMay\\netflix-may-trim-audio.wav"));
        DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(ais);

        clip.start();
        Thread.sleep(40000); // Duration should match length of audio file.
        clip.close();
    }

    public static void printNetflixMay() throws InterruptedException, FileNotFoundException {
        String pathname = "C:\\Users\\salga\\Desktop\\NetflixMay\\out%04d.png_ascii_image.txt";
        for (int i = 1; i <= 1208; i++) {
            String framePath = String.format(pathname, i);
            printFrame(framePath);
            Thread.sleep(29);
        }
    }

    public static void netflixMayThreads() {
        new Thread(() -> {
            try {
                playNFMaySound();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                printNetflixMay();
            } catch (FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
