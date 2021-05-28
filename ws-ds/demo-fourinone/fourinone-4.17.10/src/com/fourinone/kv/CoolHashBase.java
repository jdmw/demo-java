package com.fourinone.kv;

import com.fourinone.FileAdapter.ByteReadParser;
import com.fourinone.FileAdapter.ByteWriteParser;

public interface CoolHashBase{
	CoolHashException chex = new CoolHashException();
	ByteWriteParser bwp = DumpAdapter.getByteWriteParser();
	ByteReadParser brp = DumpAdapter.getByteReadParser();
	ConstantBit.Target ct = ConstantBit.Target.POINT;
	DumpAdapter dumpAdapter = new DumpAdapter("");
}