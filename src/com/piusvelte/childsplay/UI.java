package com.piusvelte.childsplay;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UI extends Activity implements OnClickListener {
	private static final int duration = 1; // seconds
	private static final int sampleRate = 8000;
	private static final int numSamples = duration * sampleRate;
	
	private final double sDo1Hz = 261.63;//c
	private final double sReHz = 293.66;//d
	private final double sMiHz = 329.63;//e
	private final double sFaHz = 349.23;//f
	private final double sSoHz = 392.00;//g
	private final double sLaHz = 440.00;//a
	private final double sTiHz = 493.88;//b
	private final double sDo2Hz = 523.25;//c

	private final byte[] sSampleDo1 = genTone(sDo1Hz);
	private final byte[] sSampleRe = genTone(sReHz);
	private final byte[] sSampleMi = genTone(sMiHz);
	private final byte[] sSampleFa = genTone(sFaHz);
	private final byte[] sSampleSo = genTone(sSoHz);
	private final byte[] sSampleLa = genTone(sLaHz);
	private final byte[] sSampleTi = genTone(sTiHz);
	private final byte[] sSampleDo2 = genTone(sDo2Hz);

	private Button buttonDo1;
	private Button buttonRe;
	private Button buttonMi;
	private Button buttonFa;
	private Button buttonSo;
	private Button buttonLa;
	private Button buttonTi;
	private Button buttonDo2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		buttonDo1 = (Button) findViewById(R.id.do1);
		buttonRe = (Button) findViewById(R.id.re);
		buttonMi = (Button) findViewById(R.id.mi);
		buttonFa = (Button) findViewById(R.id.fa);
		buttonSo = (Button) findViewById(R.id.so);
		buttonLa = (Button) findViewById(R.id.la);
		buttonTi = (Button) findViewById(R.id.ti);
		buttonDo2 = (Button) findViewById(R.id.do2);

		buttonDo1.setBackgroundColor(Color.MAGENTA);
		buttonRe.setBackgroundColor(Color.parseColor("#ff800080")); //violet
		buttonMi.setBackgroundColor(Color.parseColor("#ff4b0082")); //indigo
		buttonFa.setBackgroundColor(Color.BLUE);
		buttonSo.setBackgroundColor(Color.GREEN);
		buttonLa.setBackgroundColor(Color.YELLOW);
		buttonTi.setBackgroundColor(Color.parseColor("#ffff8000")); //orange
		buttonDo2.setBackgroundColor(Color.RED);
		
		buttonDo1.setOnClickListener(this);
		buttonRe.setOnClickListener(this);
		buttonMi.setOnClickListener(this);
		buttonFa.setOnClickListener(this);
		buttonSo.setOnClickListener(this);
		buttonLa.setOnClickListener(this);
		buttonTi.setOnClickListener(this);
		buttonDo2.setOnClickListener(this);
	}

	private byte[] genTone(double freq){
		double[] sample = new double[numSamples];
		for (int i = 0; i < numSamples; ++i) sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freq));

		// convert to 16 bit pcm sound array
		// assumes the sample buffer is normalised.
		byte[] sound = new byte[2 * numSamples];
		int idx = 0;
		for (double dVal : sample) {
			short val = (short) (dVal * 32767);
			sound[idx++] = (byte) (val & 0x00ff);
			sound[idx++] = (byte) ((val & 0xff00) >>> 8);
		}
		return sound;
	}

	private void playSound(byte[] sound){
		final byte[] play = sound;
		(new Thread(new Runnable() {
			@Override
			public void run() {
				AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, numSamples, AudioTrack.MODE_STATIC);
				audioTrack.write(play, 0, numSamples);
				audioTrack.play();
			}})).start();
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 == buttonDo1) playSound(sSampleDo1);
		if (arg0 == buttonRe) playSound(sSampleRe);
		if (arg0 == buttonMi) playSound(sSampleMi);
		if (arg0 == buttonFa) playSound(sSampleFa);
		if (arg0 == buttonSo) playSound(sSampleSo);
		if (arg0 == buttonLa) playSound(sSampleLa);
		if (arg0 == buttonTi) playSound(sSampleTi);
		if (arg0 == buttonDo2) playSound(sSampleDo2);
	}
}