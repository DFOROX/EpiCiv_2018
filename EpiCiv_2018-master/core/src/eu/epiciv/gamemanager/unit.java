package eu.epiciv.gamemanager;

import com.badlogic.gdx.graphics.Texture;

public class unit {
    int x = 0;
    int y = 0;
    int pv = 0;
    int mv = 0;
    int mvt = 0;
    int dgt = 0;
    int win = 0;
    int recruit = -10;
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
        recruit = -10;
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
        recruit = -5;
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
        recruit = -15;
        type = "cavalier";
        img = new Texture(type + ".png");
    }
    public void castle(int nx, int ny){
        x = nx;
        y = ny;
        pv = 50;
        mv = 0;
        mvt = 0;
        dgt = 10;
        win = 5;
        recruit = 0;
        type = "castle";
        img = new Texture(type + ".png");
    }
    public void builder(int nx, int ny){
        x = nx;
        y = ny;
        pv = 1;
        mv = 2;
        mvt = 0;
        dgt = 0;
        win = 0;
        recruit = 20;
        type = "builder";
        img = new Texture(type + ".png");
    }
}
