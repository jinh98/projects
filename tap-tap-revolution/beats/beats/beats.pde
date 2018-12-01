/**
  * This sketch demonstrates how to use the BeatDetect object in FREQ_ENERGY mode.<br />
  * You can use <code>isKick</code>, <code>isSnare</code>, </code>isHat</code>, <code>isRange</code>, 
  * and <code>isOnset(int)</code> to track whatever kind of beats you are looking to track, they will report 
  * true or false based on the state of the analysis. To "tick" the analysis you must call <code>detect</code> 
  * with successive buffers of audio. You can do this inside of <code>draw</code>, but you are likely to miss some 
  * audio buffers if you do this. The sketch implements an <code>AudioListener</code> called <code>BeatListener</code> 
  * so that it can call <code>detect</code> on every buffer of audio processed by the system without repeating a buffer 
  * or missing one.
  * <p>
  * This sketch plays an entire song so it may be a little slow to load.
  * <p>
  * For more information about Minim and additional features, 
  * visit http://code.compartmental.net/minim/
  */

import ddf.minim.*;
import ddf.minim.analysis.*;

Minim minim;
AudioPlayer song;
BeatDetect beat;
BeatListener bl;

float kickSize, snareSize, hatSize;
int counter=0;
int start;
int elapsed;
int end=45*1000;
int current;
int last=0;
String strip1="", strip2="", strip3="";
String cs1="", cs2="", cs3="";

class BeatListener implements AudioListener
{
  private BeatDetect beat;
  private AudioPlayer source;
  
  BeatListener(BeatDetect beat, AudioPlayer source)
  {
    this.source = source;
    this.source.addListener(this);
    this.beat = beat;
  }
  
  void samples(float[] samps)
  {
    beat.detect(source.mix);
  }
  
  void samples(float[] sampsL, float[] sampsR)
  {
    beat.detect(source.mix);
  }
}

void setup()
{
  start = millis();
  size(512, 200, P3D);
  
  minim = new Minim(this);
  
  song = minim.loadFile("Ed Sheeran - Shape Of You.mp3", 1024);
  song.play();
  // a beat detection object that is FREQ_ENERGY mod
  beat = new BeatDetect(song.bufferSize(), song.sampleRate());
  
  beat.setSensitivity(10);  
  kickSize = snareSize = hatSize = 16;
  // make a new beat listener, so that we won't miss any buffers for the analysis
  bl = new BeatListener(beat, song);  
  textFont(createFont("Helvetica", 16));
  textAlign(CENTER);
}

void draw()
{
  background(0);
  
  current = millis();
  elapsed = current - start;
  if (current - last > 215){
    last = current;
  // draw a green rectangle for every detect band
  // that had an onset this frame
  float rectW = width / beat.detectSize();
  for(int i = 0; i < beat.detectSize(); ++i)
  {
    // test one frequency band for an onset
    //println("DetectSize: "+beat.detectSize());
    if ( beat.isOnset(i) )
    {
      fill(0,200,0);
      rect( i*rectW, 0, rectW, height);
    }
  }
  
  // draw an orange rectangle over the bands in 
  // the range we are querying
  int lowBand = 5;
  int highBand = 15;
  // at least this many bands must have an onset 
  // for isRange to return true
  int numberOfOnsetsThreshold = 4;
  if ( beat.isRange(lowBand, highBand, numberOfOnsetsThreshold) )
  {
    fill(232,179,2,200);
    rect(rectW*lowBand, 0, (highBand-lowBand)*rectW, height);
  }
  
  if ( beat.isKick() ) {
    println(elapsed/1000+" s");
    println("kick");
    kickSize = 32;
    strip1=strip1+"1";
    cs1=cs1+"1,";
  }else{
    cs1=cs1+"0,";
    strip1=strip1+"0";
  }
  if ( beat.isSnare() ) {
    println(elapsed/1000+" s");
    println("snare");
    snareSize = 32;
    strip2=strip2+"2";
    cs2=cs2+"2,";
  }else{
    cs2=cs2+"0,";
    strip2=strip2+"0";
  }
  if ( beat.isHat() ) {
    println(elapsed/1000+" s");
    println("hat");
    hatSize = 32;
    strip3=strip3+"3";
    cs3=cs3+"3,";
  }else{
    strip3=strip3+"0";
    cs3=cs3+"0,";
  }
  
  fill(255);
    
  textSize(kickSize);
  text("1", width/4, height/2);
  
  textSize(snareSize);
  text("2", width/2, height/2);
  
  textSize(hatSize);
  text("3", 3*width/4, height/2);
  
  kickSize = constrain(kickSize * 0.95, 16, 32);
  snareSize = constrain(snareSize * 0.95, 16, 32);
  hatSize = constrain(hatSize * 0.95, 16, 32);
  counter++;
  println("Counter: "+counter);
  }
  
  if (counter>=200){
    strip1=strip1+" ";
    strip2=strip2+" ";
    strip3=strip3+" ";
    
    cs1=cs1+" ";
    cs2=cs2+" ";
    cs3=cs3+" ";
    
    String all = strip1 + strip2 + strip3;
    String list[] = split(all, " ");
    
    String allComma = cs1 + cs2 + cs3;
    String listComma[] = split(allComma, " ");
    saveStrings("notes.txt", list);
    saveStrings("withComma.txt", listComma);
    println(strip1);
    println(strip2);
    println(strip3);
    delay(100000000);
  }
}
