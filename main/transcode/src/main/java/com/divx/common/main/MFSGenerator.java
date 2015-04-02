package com.divx.common.main;
import java.util.ArrayList;
import java.util.List;

public class MFSGenerator {
	static double PERCEPTIBLE_ASPECT = 0.01;
	static String NEWLINE = "";
	mfsDictEntry mMfsDictEntry;
	mfsDict mPresetDict;
	
	private int mOutputWidth;
	private int mOutputHeight;
	
	public int getOutputHeight() {
		return mOutputHeight;
	}
	
	public int getOutputWidth() {
		return mOutputWidth;
	}

	private int mInputWidth;
	private int mInputHeight;
	private int mAngle;
	private double mOutAspect;
	
	public static MFSGenerator CreatAVCMFS(int width, int height, int angle){
		List<Double> availableAspect = new ArrayList<Double>();
		availableAspect.add(16./9);
		return MFSGenerator.CreateTCEMFS(width, height, angle, availableAspect);
	}
	
	public static MFSGenerator CreatHEVCMFS(int width, int height, int angle){
		List<Double> availableAspect = new ArrayList<Double>();
		availableAspect.add(16./9);
		availableAspect.add(9./16);
		availableAspect.add(4./3);
		availableAspect.add(3./4);
		return MFSGenerator.CreateTCEMFS(width, height, angle, availableAspect);
	}
	
	public boolean HasMFSString() {
		return Math.abs(mInputWidth - mOutputWidth) > 1 || Math.abs(mInputHeight - mOutputHeight) > 1
				|| (mAngle % 90 != 0 || mAngle == 90 || mAngle == 180 || mAngle==270);
	}
	
	public String Createmfs() {
		if (HasMFSString()) {
			Init();
			if (mAngle % 360 != 0) {
				if (mAngle == 90 || mAngle == 270) 
					CreateCropper(mInputHeight, mInputWidth, mOutAspect);
				else
					CreateCropper(mInputWidth, mInputHeight, mOutAspect);
				CreateRotate();
			}
			return mfsString();
		}
		return "";
	}
	
	private static MFSGenerator CreateTCEMFS(int width, int height, int angle, List<Double> availableAspect) {
		Double outAspect = 16./9;
		Double inAspect = angle == 90 || angle == 270 ?  height * 1.0 / width : width*1.0 / height;
		if (availableAspect.size() > 0) {
			outAspect = availableAspect.get(0);
			for (Double aspect : availableAspect) {
				if (Math.abs(aspect - inAspect) < Math.abs(outAspect - inAspect))
					outAspect = inAspect;
			}
		} else {
			inAspect = width*1.0 / height;
		}

		return new MFSGenerator(width, height, angle, outAspect);
	}
	
	public MFSGenerator(int width, int height, int angle, double outAspect) {
		mInputWidth = width;
		mInputHeight = height;
		mAngle = angle;
		mOutAspect = outAspect;
		
		if (angle % 180 != 0) {
			InitOutput(height, width);
		} else {
			InitOutput(width, height);
		}
	}
	
	private void InitOutput(int inWidth, int inHeight) {
		double inFrameAspect = inWidth * 1.0 / inHeight;

		if (Math.abs(inFrameAspect - mOutAspect) <= PERCEPTIBLE_ASPECT) {
			mOutputWidth = inWidth;
			mOutputHeight=inHeight;
			return;
		}
		if (mOutAspect > inFrameAspect) {
			mOutputWidth = (int)((double)inHeight*mOutAspect);
			mOutputHeight = inHeight;
		} else {
			mOutputHeight=(int)(inWidth / mOutAspect);
			mOutputWidth = inWidth;
		}
	}
	
	private void Init() {
		mfsVect presetsVect = new mfsVect(1);
		mMfsDictEntry = new mfsDictEntry("presets", presetsVect);

		mfsDict filterDict = new mfsDict("filter", presetsVect.GetTab() + 1);

		presetsVect.AddNode(filterDict);
		mPresetDict = new mfsDict("", filterDict.GetTab() + 1);
		filterDict.AddNode("preset", mPresetDict);
	}
	
	private String mfsString() {
		if (mPresetDict.size() > 0) {
			StringBuilder builder = new StringBuilder();
			builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + NEWLINE);
			builder.append("<!DOCTYPE filterPresets>"  + NEWLINE);
			builder.append("<filterPresets version=\"2.1\">" + NEWLINE);

			builder.append(mMfsDictEntry.mfsString());

			builder.append("</filterPresets>"  + NEWLINE);
			return builder.toString();
		} else
			return "";
	}

	private void CreateRotate() {

		mfsDict dict = new mfsDict("rotate", mPresetDict.GetTab() + 1);
		mfsDictEntry entry = new mfsDictEntry("rotate", dict);

		dict.AddValue("_front", 0);
		dict.AddInv("_inFrames");

		dict.AddValue("_on", 1);
		dict.AddInv("_outFrames");
		dict.AddValue("_position", 0);

		mfsDict stateDict = new mfsDict("", dict.GetTab() + 1);
		stateDict.AddValue("angle", this.mAngle);
		dict.AddNode("state", stateDict);
		mPresetDict.AddEntry(entry);
	}

	private boolean CreateCropper(int width, int height, double outAspect) {
		double inFrameAspect = width * 1.0 / this.mInputHeight;

		if (Math.abs(inFrameAspect - outAspect) <= PERCEPTIBLE_ASPECT) {
			return false;
		}

		mfsDict dict = new mfsDict("cropper", mPresetDict.GetTab() + 1);
		mfsDictEntry entry = new mfsDictEntry("cropper", dict);

		dict.AddValue("_front", 0);
		dict.AddInv("_inFrames");
		dict.AddValue("_on", 1);
		dict.AddInv("_outFrames");
		dict.AddValue("_position", 0);
		dict.AddValue("cropAspect", outAspect);

		dict.AddRect("cropBox", 0, 0, width, height);
		dict.AddValue("inFrameAspect", inFrameAspect);
		dict.AddValue("mode", 1);

		int padBottom = 0;
		int padLeft = 0;
		int padRight = 0;
		int padTop = 0;

		if (outAspect > inFrameAspect) {
			padRight = (int) ((height * outAspect - width) / 2);
			if (padRight % 2 == 1)
				padRight++;
			padLeft = padRight;
		} else {
			padBottom = (int) ((width / outAspect - height) / 2);
			if (padBottom % 2 == 1)
				padBottom++;
			padTop = padBottom;
		}

		dict.AddValue("padBottom", padBottom);
		dict.AddValue("padLeft", padLeft);
		dict.AddValue("padRight", padRight);
		dict.AddValue("padTop", padTop);

		mPresetDict.AddEntry(entry);
		return true;
	}

	private abstract class mfsNode {
		int mTab = 0;

		public mfsNode(int tab) {
			mTab = tab;
		}

		public int GetTab() {
			return mTab;
		}

		public String GetTabStr() {
			String tab = "";
			//for (int i = 0; i < mTab; i++)
			//	tab += '\t';
			return tab;
		}

		abstract String mfsString();
	}

	private class IntNode extends mfsNode {
		private int mValue = 0;

		public IntNode(int value, int tab) {
			super(tab);
			mValue = value;
		}

		public String mfsString() {
			return GetTabStr() + "<i32>" + mValue + "</i32>" + NEWLINE;
		}
	}

	private class floatNode extends mfsNode {
		private double mValue = 0;

		public floatNode(float value, int tab) {
			super(tab);
			mValue = value;
		}

		public floatNode(double value, int tab) {
			super(tab);
			mValue = value;
		}

		public String mfsString() {
			String dblFormat = "<fl> %5f</fl>" + NEWLINE;
			return GetTabStr() + String.format(dblFormat, mValue);
		}
	}

	private class NodeInv extends mfsNode {
		public NodeInv(int tab) {
			super(tab);
		}

		public String mfsString() {
			return GetTabStr() + "<inv/>" + NEWLINE;
		}
	}

	private class NodeRect extends mfsNode {
		int mBottom;
		int mLleft;
		int mRight;
		int mTop;

		public NodeRect(int top, int left, int right, int bottom, int tab) {
			super(tab);

			mTop = top;
			mLleft = left;
			mRight = right;
			mBottom = bottom;
		}

		@Override
		public String mfsString() {
			final String format = "<rect bottom=\"%d\" left=\"%d\" right=\"%d\" top=\"%d\"/>" + NEWLINE;
			return GetTabStr()
					+ String.format(format, mBottom, mLleft, mRight, mTop);
		}

	}

	private class mfsKey extends mfsNode {
		private String mKey;

		public mfsKey(String key, int tab) {
			super(tab);
			mKey = key;
		}

		@Override
		public String mfsString() {
			return GetTabStr() + "<key>" + mKey + "</key>" + NEWLINE;
		}
	}

	private class mfsDictEntry extends mfsNode {
		private mfsKey mKey;
		private mfsNode mNode;

		public mfsDictEntry(String key, mfsNode node) {
			super(node.GetTab());
			mKey = new mfsKey(key, node.GetTab());
			mNode = node;
		}

		@Override
		public String mfsString() {
			String key = mKey.mfsString();
			String value = mNode.mfsString();
			return key + value;
		}

	}

	private class mfsDict extends mfsNode {
		private String mName;
		private List<mfsDictEntry> mEntrys = new ArrayList<mfsDictEntry>();

		public mfsDict(String name, int tab) {
			super(tab);
			mName = name;
		}

		public void AddValue(String key, int value) {
			AddNode(key, new IntNode(value, mTab + 1));
		}

		public void AddValue(String key, double value) {
			AddNode(key, new floatNode(value, mTab + 1));
		}

		public void AddRect(String key, int top, int left, int right, int bottom) {
			NodeRect rect = new NodeRect(top, left, right, bottom, mTab + 1);
			AddNode(key, rect);
		}

		private void AddNode(String key, mfsNode node) {
			mEntrys.add(new mfsDictEntry(key, node));

		}

		public void AddEntry(mfsDictEntry entry) {
			mEntrys.add(entry);

		}

		public void AddInv(String name) {
			AddNode(name, new NodeInv(mTab + 1));
		}

		public String mfsString() {
			StringBuilder builder = new StringBuilder();
			builder.append(GetTabStr());
			builder.append("<dict ");

			if (mName != null && !mName.isEmpty()) {
				builder.append("name=\"" + mName + "\"");
			}
			builder.append(">" + NEWLINE);
			for (mfsDictEntry entry : mEntrys) {
				builder.append(entry.mfsString());
			}
			builder.append(GetTabStr());
			builder.append("</dict>"+ NEWLINE);
			return builder.toString();
		}

		public int size() {
			return mEntrys.size();
		}
	}

	private class mfsVect extends mfsNode {
		public mfsVect(int tab) {
			super(tab);
		}

		private List<mfsNode> mVect = new ArrayList<mfsNode>();

		@Override
		public String mfsString() {
			StringBuilder builder = new StringBuilder();
			builder.append(GetTabStr());
			builder.append("<vec>" + NEWLINE);
			for (mfsNode node : mVect) {
				builder.append(node.mfsString());
			}
			builder.append(GetTabStr());

			builder.append("</vec>" + NEWLINE);
			return builder.toString();
		}

		public void AddNode(mfsNode node) {
			mVect.add(node);
		}
	}
}
