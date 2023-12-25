package com.mygdx.game.Client;

public class Chetchik {
    private String nick;
    private byte chet;
    public Chetchik(String nick,byte chet){
        this.chet=chet;
        this.nick = nick;

    }
    public void setChet(byte chet){this.chet=chet;}
    public String getNick(){return nick;}
    public byte getChet(){return chet;}
}
