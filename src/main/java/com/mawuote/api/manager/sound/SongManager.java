package com.mawuote.api.manager.sound;

import com.mawuote.api.utilities.*;
import net.minecraft.client.audio.*;
import java.util.*;

public class SongManager implements IMinecraft
{
    private final List<ISound> songs;
    private ISound Song;
    private final ISound currentSong;
    
    public SongManager() {
        this.songs = Collections.singletonList(SoundUtil.sound);
        this.Song = this.getRandomSong();
        this.currentSong = this.getRandomSong();
    }
    
    public ISound getMenuSong() {
        return this.Song = this.getRandomSong();
    }
    
    public void play() {
        if (!this.isCurrentSongPlaying()) {
            SongManager.mc.field_147127_av.func_147682_a(this.currentSong);
        }
    }
    
    public void stop() {
        if (this.isCurrentSongPlaying()) {
            SongManager.mc.field_147127_av.func_147682_a((ISound)null);
        }
    }
    
    private boolean isCurrentSongPlaying() {
        return SongManager.mc.field_147127_av.func_147692_c(this.currentSong);
    }
    
    private ISound getRandomSong() {
        final Random random = new Random();
        return this.songs.get(random.nextInt(this.songs.size()));
    }
}
