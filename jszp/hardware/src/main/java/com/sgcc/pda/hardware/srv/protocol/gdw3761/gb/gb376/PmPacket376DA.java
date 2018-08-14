/*
 * 376里的DA没有成组出现的地方，每个DA只能表示一个测量点
 */

package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacketDA;
import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

/**
 * @author luxiaochung
 */
public class PmPacket376DA implements PmPacketDA {
    private byte[] value;
    
    public PmPacket376DA(byte[] value){
        this();
        if (value.length==2) this.value = value;
    }

    public PmPacket376DA(int pn){
        value = new byte[2];
        this.setPn(pn);
    }

    public PmPacket376DA(){
        this(0);
    }

    @Override
    public PmPacket376DA setValue(byte[] value){
        if (value.length==2) this.value = value;
        else throw  new IllegalArgumentException();
        return this;
    }
    
    @Override
    public byte[] getValue(){
        return value;
    }
    
    public final PmPacket376DA setPn(int pn){
        if ((pn>=0) && (pn<=2040)){
            if (pn==0){
                value[0] = 0;
                value[1] = 0;
            }
            else {
                value[0] = (byte)(1 << ((pn-1)%8));
                value[1] = (byte)((pn-1)/8 +1);
            }
        }
        else throw  new IllegalArgumentException();
        return this;
    }

    public int getPn(){
        if ((value[0]==0)&&(value[1]==0)) return 0;
        else return (BcdUtils.bitSetOfByte(value[0])+8*(value[1]-1));
    }

    @Override
    public String toString(){
        StringBuilder buff = new StringBuilder();
        buff.append("pn=").append(this.getPn());
        return buff.toString();
    }
}