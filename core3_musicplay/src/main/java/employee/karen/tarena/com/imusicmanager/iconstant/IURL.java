package employee.karen.tarena.com.imusicmanager.iconstant;

/**
 * Created by pjy on 2017/6/2.
 */

public interface IURL {
    String ROOT="http://172.60.50.150:8000/MusicServer/";
    String MUSICLIST_URL=ROOT+"loadMusics.jsp";
    //播放控制界面发给服务的广播动作
    String PLAYMUSIC_ACTION="com.tarena.karen.player.PLAY";
    String PUASEMUSIC_ACTION="com.tarena.karen.player.PAUSE";
    String SEEKUPDATE_ACTION="com.tarena.karen.player.SEEKUPDATE";
    //服务发给播放控件界面的广播
    String PLAYNEXT_ACTION="com.tarena.karen.player.NEXT";
    //服务发给播放控制界面的广播用来改变播放的进度
    String UPDATEPROGRESS_ACTION="com.tarena.karen.player.UPDATEPROGRESS";
    //音乐列表activity发给小组件的音乐加载完成一个广播
    String MUSICSLOAD_ACTION="com.tarena.karen.player.MUSICSLOAD";

    String PLAYNEXTWIDGET_ACTION="com.tarena.karen.player.WIDGETNEXT";

    //服务发给小组件广播
    //更新小组件的UI(显示暂停,隐藏播放)
    String UPDATEPAUSEWIDGET_ACTION="com.tarena.karen.player.HIDEPLAY";
    String UPDATEPLAYWIDGET_ACTION="com.tarena.karen.player.HIDEPAUSE";
}
