package eu.epiciv.scrollmanager;

import com.badlogic.gdx.graphics.Texture;

public class unit {
    int x = 0;
    int y = 0;
    int pv = 0;
    int mv = 0;
    int mvt = 0;
    int dgt = 0;
    int win = 0;
    String type = "???";
    Texture img;
    public void lancier(int nx, int ny){
        x = nx;
        y = ny;
        pv = 10;
        mv = 2;
        mvt = 0;
        dgt = 5;
        win = 6;
        type = "lancier";
        img = new Texture(type + ".png");
    }
    public void epeiste(int nx, int ny){
        x = nx;
        y = ny;
        pv = 20;
        mv = 2;
        mvt = 0;
        dgt = 3;
        win = 4;
        type = "épéiste";
        img = new Texture(type + ".png");
    }
    public void cavalier(int nx, int ny){
        x = nx;
        y = ny;
        pv = 5;
        mv = 4;
        mvt = 0;
        dgt = 10;
        win = 5;
        type = "cavalier";
        img = new Texture(type + ".png");
    }
}
