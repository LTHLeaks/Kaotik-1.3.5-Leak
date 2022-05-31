package com.mawuote.client.modules.combat;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Criticals extends Module
{
    public static ValueEnum mode;
    
    public Criticals() {
        super("Criticals", "Criticals", "haha funny attack", ModuleCategory.PLAYER);
    }
    
    @SubscribeEvent
    public void onAttack(final AttackEntityEvent event) {
        if (Criticals.mc.field_71439_g.func_70090_H() || Criticals.mc.field_71439_g.func_180799_ab()) {
            return;
        }
        if (Criticals.mc.field_71439_g.field_70122_E) {
            if (Criticals.mode.getValue().equals(modes.Packet)) {
                Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u + 0.1625, Criticals.mc.field_71439_g.field_70161_v, false));
                Criticals.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(Criticals.mc.field_71439_g.field_70165_t, Criticals.mc.field_71439_g.field_70163_u, Criticals.mc.field_71439_g.field_70161_v, false));
            }
            else {
                Criticals.mc.field_71439_g.func_70664_aZ();
                if (Criticals.mode.getValue().equals(modes.Jump)) {
                    final EntityPlayerSP field_71439_g = Criticals.mc.field_71439_g;
                    field_71439_g.field_70181_x /= 2.0;
                }
            }
            Criticals.mc.field_71439_g.func_71009_b(event.getTarget());
        }
    }
    
    static {
        Criticals.mode = new ValueEnum("Mode", "Mode", "", modes.Packet);
    }
    
    public enum modes
    {
        Packet, 
        Jump;
    }
}
