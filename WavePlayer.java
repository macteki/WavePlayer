/******************************************************************************
* File : WavePlayer.java
* Author : Ivan Macteki @ http://java.macteki.com/
* Description :
*   Play sound file with a progress bar.  Support .wav file format only.
*   Sample wave file included : epic_loop.wav, twoTone2.wav
* Compile and Run :
*   javac *.java
*   java WavePlayer
* Tested with : OracleJDK 1.8
* License:
*  WavePlayer.java : by Macteki,MIT license, http://java.macteki.com/p/license.html
*  epic_loop.wav : Orchestral Adventure by RevampedPRO, CC BY 3.0 license 
*                  http://opengameart.org/content/orchestral-adventure 
*  twoTone2.wav : by Kenney, CC0 license, 
*  http://opengameart.org/content/63-digital-sound-effects-lasers-phasers-space-etc
******************************************************************************/

import java.io.File;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class WavePlayer 
{

  String fileName1= "epic_loop.wav";
  String fileName2= "twoTone2.wav";

  Clip openWaveFile(String fileName) 
  {
    try {
      File file=new File(fileName);

      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      AudioFormat format = stream.getFormat();

      DataLine.Info info = new DataLine.Info(Clip.class, format);
      Clip clip = (Clip) AudioSystem.getLine(info);

      clip.open(stream);
      return clip;
    } catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }    
  }

  private void init() 
  {
    JFrame frame = new JFrame("Wave Player - by Macteki");
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    final JSlider progress = new JSlider(0,10000,0);        
    progress.setEnabled(false);
    panel.add(progress,BorderLayout.CENTER);
    frame.add(panel);
    frame.setBounds(200, 100, 800, 250);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    Clip clip=openWaveFile(fileName1);
    clip.start();
    
    Timer timer=new Timer(30, (e)->updateProgress(clip, progress));
    timer.start();
  }


  private void updateProgress(Clip clip, JSlider progress)
  {
    long duration=clip.getFrameLength();
    long current=clip.getFramePosition();
    double p=(double)current/duration;  // current progress between 0.0 to 1.0

    // multiply p by max (e.g. 10000), this is the progress value
    int max=progress.getMaximum();
    progress.setValue((int)(max*p));
    
  }

  public static void main(String[] args) 
  {
    SwingUtilities.invokeLater(() -> new WavePlayer().init());
  }
}


