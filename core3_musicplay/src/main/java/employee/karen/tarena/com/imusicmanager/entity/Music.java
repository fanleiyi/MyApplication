package employee.karen.tarena.com.imusicmanager.entity;

import java.io.Serializable;

/**
 * Created by pjy on 2017/6/2.
 * 封装音乐数据的实体类
 */

public class Music implements Serializable{
    private int id;//音乐的编号
    private String album;//专辑名称
    private String albumpic;//专辑图片路径
    private String author;//作词
    private String composer;//作曲
    private String downcount;//下载次数
    private String durationtime;//播放时长
    private String favcount;//赞数
    private String musicpath;//音乐文件路径
    private String name;//歌曲名称
    private String singer;//演唱

    public Music() {
    }

    public Music(int id, String album, String albumpic, String author, String composer, String downcount, String durationtime, String favcount, String musicpath, String name, String singer) {
        this.id = id;
        this.album = album;
        this.albumpic = albumpic;
        this.author = author;
        this.composer = composer;
        this.downcount = downcount;
        this.durationtime = durationtime;
        this.favcount = favcount;
        this.musicpath = musicpath;
        this.name = name;
        this.singer = singer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumpic() {
        return albumpic;
    }

    public void setAlbumpic(String albumpic) {
        this.albumpic = albumpic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getDowncount() {
        return downcount;
    }

    public void setDowncount(String downcount) {
        this.downcount = downcount;
    }

    public String getDurationtime() {
        return durationtime;
    }

    public void setDurationtime(String durationtime) {
        this.durationtime = durationtime;
    }

    public String getFavcount() {
        return favcount;
    }

    public void setFavcount(String favcount) {
        this.favcount = favcount;
    }

    public String getMusicpath() {
        return musicpath;
    }

    public void setMusicpath(String musicpath) {
        this.musicpath = musicpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", album='" + album + '\'' +
                ", albumpic='" + albumpic + '\'' +
                ", author='" + author + '\'' +
                ", composer='" + composer + '\'' +
                ", downcount='" + downcount + '\'' +
                ", durationtime='" + durationtime + '\'' +
                ", favcount='" + favcount + '\'' +
                ", musicpath='" + musicpath + '\'' +
                ", name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                '}';
    }
}
