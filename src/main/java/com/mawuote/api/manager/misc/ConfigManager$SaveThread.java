package com.mawuote.api.manager.misc;

import com.mawuote.*;

public static class SaveThread extends Thread
{
    @Override
    public void run() {
        Kaotik.CONFIG_MANAGER.save();
    }
}
