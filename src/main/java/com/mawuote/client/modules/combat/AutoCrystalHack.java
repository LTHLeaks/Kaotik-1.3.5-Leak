package com.mawuote.client.modules.combat;

import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.utilities.crystal.*;
import net.minecraft.entity.*;
import com.mawuote.api.utilities.math.*;
import com.mawuote.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import java.util.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.api.utilities.render.*;
import net.minecraft.util.math.*;
import java.awt.*;
import com.mawuote.api.manager.event.impl.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mawuote.api.manager.event.impl.network.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import com.mawuote.api.utilities.entity.*;

public class AutoCrystalHack extends Module
{
    public static AutoCrystalHack INSTANCE;
    private EntityEnderCrystal targetCrystal;
    private BlockPos targetPosition;
    private final List<Integer> blacklist;
    List<FadePosition> fadePositions;
    public static BlockPos renderPosition;
    private float damageNumber;
    public static boolean isSequential;
    public EntityPlayer target;
    private int breakTicks;
    private int placeTicks;
    public static ValueBoolean breakMode;
    public static ValueNumber breakDelay;
    public static ValueNumber breakRange;
    public static ValueNumber breakWallsRange;
    public static ValueBoolean antiWeakness;
    public static ValueBoolean inhibit;
    public static ValueBoolean sequential;
    public static ValueBoolean sync;
    public static ValueBoolean place;
    public static ValueNumber placeDelay;
    public static ValueNumber placeRange;
    public static ValueNumber placeWallsRange;
    public static ValueBoolean placeUnderBlock;
    public static ValueEnum switchMode;
    public static ValueEnum multiPlace;
    public static ValueBoolean holePlace;
    public static ValueBoolean rotation;
    public static ValueEnum timing;
    public static ValueNumber targetRange;
    public static ValueEnum swing;
    public static ValueBoolean packetSwing;
    public static ValueNumber minimumDamage;
    public static ValueNumber maxSelfDamage;
    public static ValueBoolean facePlace;
    public static ValueNumber facePlaceHealth;
    public static ValueBoolean armorBreaker;
    public static ValueNumber armorPercent;
    public static ValueEnum render;
    public static ValueEnum fill;
    public static ValueEnum outline;
    public static ValueBoolean renderDamage;
    public static ValueNumber shrinkSpeed;
    public static ValueNumber fadeDuration;
    public static ValueNumber lineWidth;
    public static ValueColor fillColor;
    public static ValueColor outlineColor;
    
    public AutoCrystalHack() {
        super("AutoCrystal", "Auto Crystal", "haha boom", ModuleCategory.COMBAT);
        this.targetCrystal = null;
        this.targetPosition = null;
        this.blacklist = new ArrayList<Integer>();
        this.fadePositions = new ArrayList<FadePosition>();
        this.damageNumber = 0.0f;
        this.target = null;
        AutoCrystalHack.INSTANCE = this;
    }
    
    @Override
    public void onMotionUpdate() {
        this.doAutoCrystal();
    }
    
    public void doAutoCrystal() {
        if (AutoCrystalHack.mc.field_71439_g == null || AutoCrystalHack.mc.field_71441_e == null) {
            return;
        }
        double maxCrystalDamage = 0.0;
        double maxPositionDamage = 0.0;
        if (AutoCrystalHack.place.getValue() && this.placeTicks++ > AutoCrystalHack.placeDelay.getValue().intValue()) {
            this.placeTicks = 0;
            final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.func_191196_a();
            for (final BlockPos position : CrystalUtils.getSphere(AutoCrystalHack.placeRange.getValue().floatValue(), true, false)) {
                if (AutoCrystalHack.mc.field_71441_e.func_180495_p(position).func_177230_c() == Blocks.field_150350_a) {
                    continue;
                }
                if (!CrystalUtils.canPlaceCrystal(position, AutoCrystalHack.placeUnderBlock.getValue(), AutoCrystalHack.multiPlace.getValue().equals(MultiPlaceModes.Static) || (AutoCrystalHack.multiPlace.getValue().equals(MultiPlaceModes.Dynamic) && CrystalUtils.isEntityMoving((EntityLivingBase)AutoCrystalHack.mc.field_71439_g) && CrystalUtils.isEntityMoving((EntityLivingBase)this.target)), AutoCrystalHack.holePlace.getValue())) {
                    continue;
                }
                positions.add((Object)position);
            }
            for (final EntityPlayer player : AutoCrystalHack.mc.field_71441_e.field_73010_i) {
                if (AutoCrystalHack.mc.field_71439_g.func_70068_e((Entity)player) > MathUtils.square(AutoCrystalHack.targetRange.getValue().floatValue())) {
                    continue;
                }
                if (player == AutoCrystalHack.mc.field_71439_g) {
                    continue;
                }
                if (Kaotik.FRIEND_MANAGER.isFriend(player.func_70005_c_())) {
                    continue;
                }
                if (player.field_70128_L) {
                    continue;
                }
                if (player.func_110143_aJ() <= 0.0f) {
                    continue;
                }
                for (final BlockPos position2 : positions) {
                    final float targetDamage = this.filterPosition(position2, player);
                    if (targetDamage == -1.0f) {
                        continue;
                    }
                    if (targetDamage <= maxPositionDamage) {
                        continue;
                    }
                    maxPositionDamage = targetDamage;
                    this.targetPosition = position2;
                    this.damageNumber = targetDamage;
                    this.target = player;
                }
            }
            if (this.targetPosition != null) {
                final int slot = InventoryUtils.findItem(Items.field_185158_cP, 0, 9);
                final int lastSlot = AutoCrystalHack.mc.field_71439_g.field_71071_by.field_70461_c;
                if (!AutoCrystalHack.switchMode.getValue().equals(SwitchModes.None) && slot != -1) {
                    InventoryUtils.switchSlot(slot, AutoCrystalHack.switchMode.getValue().equals(SwitchModes.Silent));
                }
                if (AutoCrystalHack.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP || AutoCrystalHack.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP || AutoCrystalHack.switchMode.getValue().equals(SwitchModes.Silent)) {
                    AutoCrystalHack.renderPosition = this.targetPosition;
                    final RayTraceResult result = AutoCrystalHack.mc.field_71441_e.func_72933_a(new Vec3d(AutoCrystalHack.mc.field_71439_g.field_70165_t, AutoCrystalHack.mc.field_71439_g.field_70163_u + AutoCrystalHack.mc.field_71439_g.func_70047_e(), AutoCrystalHack.mc.field_71439_g.field_70161_v), new Vec3d(this.targetPosition.func_177958_n() + 0.5, this.targetPosition.func_177956_o() - 0.5, this.targetPosition.func_177952_p() + 0.5));
                    final EnumFacing facing = (result == null || result.field_178784_b == null) ? EnumFacing.UP : result.field_178784_b;
                    AutoCrystalHack.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPosition, facing, AutoCrystalHack.switchMode.getValue().equals(SwitchModes.Silent) ? EnumHand.MAIN_HAND : ((AutoCrystalHack.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND), 0.5f, 0.5f, 0.5f));
                    AutoCrystalHack.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(AutoCrystalHack.switchMode.getValue().equals(SwitchModes.Silent) ? EnumHand.MAIN_HAND : ((AutoCrystalHack.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND)));
                }
                else {
                    AutoCrystalHack.renderPosition = null;
                }
                if (AutoCrystalHack.switchMode.getValue().equals(SwitchModes.Silent) && lastSlot != -1) {
                    InventoryUtils.switchSlot(lastSlot, AutoCrystalHack.switchMode.getValue().equals(SwitchModes.Silent));
                }
            }
            else {
                AutoCrystalHack.renderPosition = null;
            }
            this.targetPosition = null;
        }
        if (AutoCrystalHack.breakMode.getValue() && this.breakTicks++ > AutoCrystalHack.breakDelay.getValue().intValue()) {
            this.breakTicks = 0;
            for (final EntityPlayer player2 : AutoCrystalHack.mc.field_71441_e.field_73010_i) {
                if (AutoCrystalHack.mc.field_71439_g.func_70068_e((Entity)player2) > MathUtils.square(AutoCrystalHack.targetRange.getValue().floatValue())) {
                    continue;
                }
                if (player2 == AutoCrystalHack.mc.field_71439_g) {
                    continue;
                }
                if (Kaotik.FRIEND_MANAGER.isFriend(player2.func_70005_c_())) {
                    continue;
                }
                if (player2.field_70128_L) {
                    continue;
                }
                if (player2.func_110143_aJ() <= 0.0f) {
                    continue;
                }
                for (final Entity entity : new ArrayList(AutoCrystalHack.mc.field_71441_e.field_72996_f)) {
                    if (!(entity instanceof EntityEnderCrystal)) {
                        continue;
                    }
                    final EntityEnderCrystal crystal = (EntityEnderCrystal)entity;
                    if (this.blacklist.contains(crystal.func_145782_y()) && AutoCrystalHack.inhibit.getValue()) {
                        continue;
                    }
                    final double targetDamage2 = this.filterCrystal(crystal, player2);
                    if (targetDamage2 == -1.0) {
                        continue;
                    }
                    if (targetDamage2 <= maxCrystalDamage) {
                        continue;
                    }
                    maxCrystalDamage = targetDamage2;
                    this.targetCrystal = crystal;
                    this.target = player2;
                }
            }
            if (this.targetCrystal != null) {
                final int swordSlot = InventoryUtils.findItem(Items.field_151048_u, 0, 9);
                final int appleSlot = InventoryUtils.findItem(Items.field_151153_ao, 0, 9);
                if (AutoCrystalHack.antiWeakness.getValue() && AutoCrystalHack.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t) && swordSlot != -1) {
                    if (!(AutoCrystalHack.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword)) {
                        InventoryUtils.switchSlot(swordSlot, false);
                    }
                }
                else if (AutoCrystalHack.antiWeakness.getValue() && !AutoCrystalHack.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t) && appleSlot != -1 && AutoCrystalHack.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) {
                    InventoryUtils.switchSlot(appleSlot, false);
                }
                AutoCrystalHack.mc.field_71442_b.func_78764_a((EntityPlayer)AutoCrystalHack.mc.field_71439_g, (Entity)this.targetCrystal);
                this.swingItem();
                this.targetCrystal = null;
            }
        }
    }
    
    @Override
    public void onRender3D(final EventRender3D event) {
        if (!AutoCrystalHack.render.getValue().equals(RenderModes.None)) {
            if (AutoCrystalHack.render.getValue().equals(RenderModes.Normal) || AutoCrystalHack.render.getValue().equals(RenderModes.Fade) || AutoCrystalHack.render.getValue() == RenderModes.Size) {
                if (AutoCrystalHack.renderPosition != null) {
                    if (AutoCrystalHack.render.getValue() == RenderModes.Fade || AutoCrystalHack.render.getValue() == RenderModes.Size) {
                        this.fadePositions.removeIf(pos -> pos.position.equals((Object)AutoCrystalHack.renderPosition));
                        this.fadePositions.add(new FadePosition(AutoCrystalHack.renderPosition));
                    }
                    if (AutoCrystalHack.fill.getValue().equals(Renders.Normal)) {
                        RenderUtils.drawBox(AutoCrystalHack.renderPosition, AutoCrystalHack.fillColor.getValue());
                    }
                    if (AutoCrystalHack.outline.getValue().equals(Renders.Normal)) {
                        RenderUtils.drawBlockOutline(new AxisAlignedBB(AutoCrystalHack.renderPosition.func_177958_n() - AutoCrystalHack.mc.func_175598_ae().field_78730_l, AutoCrystalHack.renderPosition.func_177956_o() - AutoCrystalHack.mc.func_175598_ae().field_78731_m, AutoCrystalHack.renderPosition.func_177952_p() - AutoCrystalHack.mc.func_175598_ae().field_78728_n, AutoCrystalHack.renderPosition.func_177958_n() + 1 - AutoCrystalHack.mc.func_175598_ae().field_78730_l, AutoCrystalHack.renderPosition.func_177956_o() + 1 - AutoCrystalHack.mc.func_175598_ae().field_78731_m, AutoCrystalHack.renderPosition.func_177952_p() + 1 - AutoCrystalHack.mc.func_175598_ae().field_78728_n), AutoCrystalHack.outlineColor.getValue(), AutoCrystalHack.lineWidth.getValue().floatValue());
                    }
                }
                if (AutoCrystalHack.render.getValue().equals(RenderModes.Fade)) {
                    this.fadePositions.forEach(pos -> {
                        if (!pos.getPosition().equals((Object)AutoCrystalHack.renderPosition)) {
                            final long time = System.currentTimeMillis();
                            final long duration = time - pos.getStartTime();
                            if (duration < AutoCrystalHack.fadeDuration.getValue().intValue() * 100L) {
                                final float opacity = AutoCrystalHack.fillColor.getValue().getAlpha() / 255.0f - duration / (float)(AutoCrystalHack.fadeDuration.getValue().intValue() * 100L);
                                final int alpha = MathHelper.func_76125_a((int)(opacity * 255.0f), 0, 255);
                                if (AutoCrystalHack.fill.getValue().equals(Renders.Normal)) {
                                    RenderUtils.drawBox(pos.getPosition(), new Color(AutoCrystalHack.fillColor.getValue().getRed(), AutoCrystalHack.fillColor.getValue().getGreen(), AutoCrystalHack.fillColor.getValue().getBlue(), alpha));
                                }
                                if (AutoCrystalHack.outline.getValue().equals(Renders.Normal)) {
                                    RenderUtils.prepare(7);
                                    RenderUtils.drawBoundingBoxBlockPos(pos.getPosition(), AutoCrystalHack.lineWidth.getValue().floatValue(), AutoCrystalHack.outlineColor.getValue().getRed(), AutoCrystalHack.outlineColor.getValue().getGreen(), AutoCrystalHack.outlineColor.getValue().getBlue(), alpha);
                                    RenderUtils.release();
                                }
                            }
                        }
                        return;
                    });
                    this.fadePositions.removeIf(fadePosition -> System.currentTimeMillis() - fadePosition.getStartTime() >= AutoCrystalHack.fadeDuration.getValue().intValue() * 100L);
                }
                else if (AutoCrystalHack.render.getValue().equals(RenderModes.Size)) {
                    this.fadePositions.stream().distinct().forEach(p -> {
                        if (!p.position.equals((Object)AutoCrystalHack.renderPosition)) {
                            final AxisAlignedBB bb = RenderUtils.fixBB(new AxisAlignedBB(p.position));
                            final long time2 = System.currentTimeMillis();
                            final long duration2 = time2 - p.startTime;
                            final float startAlpha = AutoCrystalHack.fillColor.getValue().getAlpha() / 255.0f;
                            if (duration2 < AutoCrystalHack.shrinkSpeed.getValue().intValue() * 10L) {
                                final float opacity2 = startAlpha - duration2 / (float)(AutoCrystalHack.shrinkSpeed.getValue().intValue() * 10);
                                final float opacity3 = MathHelper.func_76131_a(opacity2, -1.0f, 0.0f);
                                final AxisAlignedBB bb2 = bb.func_186664_h((double)(-opacity3));
                                RenderUtils.drawFilledBox(bb2, AutoCrystalHack.fillColor.getValue().getRGB());
                                RenderUtils.drawBlockOutline(bb2, AutoCrystalHack.outlineColor.getValue(), 1.0f);
                            }
                        }
                        return;
                    });
                    this.fadePositions.removeIf(p -> System.currentTimeMillis() - p.startTime >= AutoCrystalHack.shrinkSpeed.getValue().intValue() * 10L || AutoCrystalHack.mc.field_71441_e.func_180495_p(p.position).func_177230_c() == Blocks.field_150350_a);
                }
            }
            if (AutoCrystalHack.renderDamage.getValue() && AutoCrystalHack.renderPosition != null) {
                CrystalUtils.drawText(AutoCrystalHack.renderPosition, ((Math.floor(this.damageNumber) == this.damageNumber) ? Integer.valueOf((int)this.damageNumber) : String.format("%.1f", this.damageNumber)) + "");
            }
        }
    }
    
    @SubscribeEvent
    public void onCrystalAttack(final EventCrystalAttack event) {
        this.blacklist.add(event.getEntityID());
    }
    
    @SubscribeEvent
    public void onPacketReceive(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect && AutoCrystalHack.sync.getValue()) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.func_186977_b() == SoundCategory.BLOCKS && packet.func_186978_a() == SoundEvents.field_187539_bB) {
                for (final Entity entity : new ArrayList(AutoCrystalHack.mc.field_71441_e.field_72996_f)) {
                    if (entity instanceof EntityEnderCrystal && entity.func_70092_e(packet.func_149207_d(), packet.func_149211_e(), packet.func_149210_f()) <= 36.0) {
                        entity.func_70106_y();
                    }
                }
            }
        }
        if (event.getPacket() instanceof SPacketSpawnObject && AutoCrystalHack.sequential.getValue()) {
            final SPacketSpawnObject packet2 = (SPacketSpawnObject)event.getPacket();
            if (this.target == null) {
                return;
            }
            if (packet2.func_148993_l() == 51 && AutoCrystalHack.breakMode.getValue()) {
                final EntityEnderCrystal crystal = new EntityEnderCrystal((World)AutoCrystalHack.mc.field_71441_e, packet2.func_186880_c(), packet2.func_186882_d(), packet2.func_186881_e());
                if (this.target != null && this.filterCrystal(crystal, this.target) != -1.0f) {
                    if (this.blacklist.contains(packet2.func_149001_c())) {
                        return;
                    }
                    AutoCrystalHack.isSequential = true;
                    final CPacketUseEntity crystalPacket = new CPacketUseEntity();
                    crystalPacket.field_149567_a = packet2.func_149001_c();
                    crystalPacket.field_149566_b = CPacketUseEntity.Action.ATTACK;
                    AutoCrystalHack.mc.field_71439_g.field_71174_a.func_147297_a((Packet)crystalPacket);
                    if (AutoCrystalHack.mc.field_71442_b.func_178889_l() != GameType.SPECTATOR) {
                        AutoCrystalHack.mc.field_71439_g.func_184821_cY();
                    }
                    this.swingItem();
                    this.blacklist.add(crystalPacket.field_149567_a);
                    AutoCrystalHack.isSequential = false;
                }
            }
        }
    }
    
    public float filterCrystal(final EntityEnderCrystal crystal, final EntityPlayer target) {
        Label_0074: {
            if (AutoCrystalHack.mc.field_71439_g.func_70685_l((Entity)crystal)) {
                if (AutoCrystalHack.mc.field_71439_g.func_70068_e((Entity)crystal) <= MathUtils.square(AutoCrystalHack.breakRange.getValue().floatValue())) {
                    break Label_0074;
                }
            }
            else if (AutoCrystalHack.mc.field_71439_g.func_70068_e((Entity)crystal) <= MathUtils.square(AutoCrystalHack.breakWallsRange.getValue().floatValue())) {
                break Label_0074;
            }
            return -1.0f;
        }
        if (crystal.field_70128_L) {
            return -1.0f;
        }
        final float targetDamage = DamageUtils.calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, (EntityLivingBase)target);
        final float selfDamage = DamageUtils.calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, (EntityLivingBase)AutoCrystalHack.mc.field_71439_g);
        return this.returnDamage(target, targetDamage, selfDamage);
    }
    
    public float filterPosition(final BlockPos position, final EntityPlayer target) {
        Label_0068: {
            if (CrystalUtils.canSeePos(position)) {
                if (AutoCrystalHack.mc.field_71439_g.func_174818_b(position) <= MathUtils.square(AutoCrystalHack.placeRange.getValue().floatValue())) {
                    break Label_0068;
                }
            }
            else if (AutoCrystalHack.mc.field_71439_g.func_174818_b(position) <= MathUtils.square(AutoCrystalHack.placeWallsRange.getValue().floatValue())) {
                break Label_0068;
            }
            return -1.0f;
        }
        final float targetDamage = DamageUtils.calculateDamage(position.func_177958_n() + 0.5, position.func_177956_o() + 1.0, position.func_177952_p() + 0.5, (EntityLivingBase)target);
        final float selfDamage = DamageUtils.calculateDamage(position.func_177958_n() + 0.5, position.func_177956_o() + 1.0, position.func_177952_p() + 0.5, (EntityLivingBase)AutoCrystalHack.mc.field_71439_g);
        return this.returnDamage(target, targetDamage, selfDamage);
    }
    
    private float returnDamage(final EntityPlayer target, final float targetDamage, final float selfDamage) {
        if (targetDamage < this.getMinimumDamage((EntityLivingBase)target) && targetDamage < target.func_110143_aJ() + target.func_110139_bj()) {
            return -1.0f;
        }
        if (selfDamage > AutoCrystalHack.maxSelfDamage.getValue().floatValue()) {
            return -1.0f;
        }
        if (AutoCrystalHack.mc.field_71439_g.func_110143_aJ() + AutoCrystalHack.mc.field_71439_g.func_110139_bj() <= selfDamage) {
            return -1.0f;
        }
        return targetDamage;
    }
    
    public void swingItem() {
        if (!AutoCrystalHack.swing.getValue().equals(Hands.None)) {
            if (AutoCrystalHack.packetSwing.getValue()) {
                if (AutoCrystalHack.swing.getValue().equals(Hands.Mainhand)) {
                    AutoCrystalHack.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                }
                else if (AutoCrystalHack.swing.getValue().equals(Hands.Offhand)) {
                    AutoCrystalHack.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                }
            }
            else if (AutoCrystalHack.swing.getValue().equals(Hands.Mainhand)) {
                AutoCrystalHack.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
            }
            else if (AutoCrystalHack.swing.getValue().equals(Hands.Offhand)) {
                AutoCrystalHack.mc.field_71439_g.func_184609_a(EnumHand.OFF_HAND);
            }
        }
    }
    
    public float getMinimumDamage(final EntityLivingBase entity) {
        if ((AutoCrystalHack.facePlace.getValue() && entity.func_110143_aJ() + entity.func_110139_bj() < AutoCrystalHack.facePlaceHealth.getValue().floatValue()) || (AutoCrystalHack.armorBreaker.getValue() && DamageUtils.shouldBreakArmor(entity, (float)AutoCrystalHack.armorPercent.getValue().intValue()))) {
            return 1.0f;
        }
        return AutoCrystalHack.minimumDamage.getValue().floatValue();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        AutoCrystalHack.isSequential = false;
        this.target = null;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        AutoCrystalHack.isSequential = false;
        this.target = null;
    }
    
    @Override
    public void onLogout() {
        this.blacklist.clear();
    }
    
    static {
        AutoCrystalHack.renderPosition = null;
        AutoCrystalHack.isSequential = false;
        AutoCrystalHack.breakMode = new ValueBoolean("Break", "Break", "Breaks the crystals.", true);
        AutoCrystalHack.breakDelay = new ValueNumber("BreakDelay", "BreakDelay", "The delay for breaking.", 0, 0, 20);
        AutoCrystalHack.breakRange = new ValueNumber("BreakRange", "BreakRange", "The range for breaking.", 5.0f, 0.0f, 6.0f);
        AutoCrystalHack.breakWallsRange = new ValueNumber("BreakWallsRange", "BreakWallsRange", "The range for breaking through walls.", 3.5f, 0.0f, 6.0f);
        AutoCrystalHack.antiWeakness = new ValueBoolean("AntiWeakness", "AntiWeakness", "Switches to a sword when you have weakness.", false);
        AutoCrystalHack.inhibit = new ValueBoolean("Inhibit", "Inhibit", "Prevents an a crystal which is going to explode from being attacked again.", true);
        AutoCrystalHack.sequential = new ValueBoolean("Sequential", "Sequential", "Breaks crystals when they spawn. Good for strictless servers.", true);
        AutoCrystalHack.sync = new ValueBoolean("Sync", "Sync", "Syncs crystals based on sounds.", true);
        AutoCrystalHack.place = new ValueBoolean("Place", "Place", "Places the crystals.", true);
        AutoCrystalHack.placeDelay = new ValueNumber("PlaceDelay", "PlaceDelay", "The delay for placing.", 0, 0, 20);
        AutoCrystalHack.placeRange = new ValueNumber("PlaceRange", "PlaceRange", "The range for placing.", 5.0f, 0.0f, 6.0f);
        AutoCrystalHack.placeWallsRange = new ValueNumber("PlaceWallsRange", "PlaceWallsRange", "The range for breaking through walls.", 3.5f, 0.0f, 6.0f);
        AutoCrystalHack.placeUnderBlock = new ValueBoolean("PlaceUnderBlock", "PlaceUnderBlock", "Places under blocks.", false);
        AutoCrystalHack.switchMode = new ValueEnum("Switch", "Switch", "Automatically switches to a crystal.", SwitchModes.None);
        AutoCrystalHack.multiPlace = new ValueEnum("MultiPlace", "MultiPlace", "Places in more positions than one.", MultiPlaceModes.None);
        AutoCrystalHack.holePlace = new ValueBoolean("HolePlace", "HolePlace", "Places in the hole when the player jumps.", true);
        AutoCrystalHack.rotation = new ValueBoolean("Rotation", "Rotation", "Rotates to the crystal and position when placing.", false);
        AutoCrystalHack.timing = new ValueEnum("Timing", "Timing", "The timing for the breaking.", Timings.Break);
        AutoCrystalHack.targetRange = new ValueNumber("TargetRange", "TargetRange", "The range for targeting.", 15.0f, 0.0f, 30.0f);
        AutoCrystalHack.swing = new ValueEnum("Swing", "Swing", "The hand to swing with.", Hands.Mainhand);
        AutoCrystalHack.packetSwing = new ValueBoolean("PacketSwing", "PacketSwing", "Swings serverside but not clientside.", false);
        AutoCrystalHack.minimumDamage = new ValueNumber("MinimumDamage", "MinimumDamage", "The minimum damage that is required for the target.", 6.0f, 0.0f, 36.0f);
        AutoCrystalHack.maxSelfDamage = new ValueNumber("MaxSelfDamage", "MaxSelfDamage", "The minimum damage that is required for the target.", 6.0f, 0.0f, 36.0f);
        AutoCrystalHack.facePlace = new ValueBoolean("FacePlace", "FacePlace", "Faceplaces the target when the opportunity is right.", true);
        AutoCrystalHack.facePlaceHealth = new ValueNumber("FacePlaceHealth", "FacePlaceHealth", "The health at which faceplacing would start.", 12.0f, 0.0f, 36.0f);
        AutoCrystalHack.armorBreaker = new ValueBoolean("ArmorBreaker", "ArmorBreaker", "Starts faceplacing the enemy when their armor is low.", true);
        AutoCrystalHack.armorPercent = new ValueNumber("ArmorPercent", "ArmorPercent", "The percent required for the armor breaking to start.", 20, 0, 100);
        AutoCrystalHack.render = new ValueEnum("Render", "Render", "Renders the current target position.", RenderModes.Normal);
        AutoCrystalHack.fill = new ValueEnum("Fill", "Fill", "The mode for filling the position.", Renders.Normal);
        AutoCrystalHack.outline = new ValueEnum("Outline", "Outline", "The mode for outlining the position.", Renders.Normal);
        AutoCrystalHack.renderDamage = new ValueBoolean("RenderDamage", "RenderDamage", "Renders the amount of damage that the position does.", false);
        AutoCrystalHack.shrinkSpeed = new ValueNumber("ShrinkSpeed", "ShrinkSpeed", "", 150, 10, 500);
        AutoCrystalHack.fadeDuration = new ValueNumber("FadeDuration", "FadeDuration", "The duration of the fade.", 15, 1, 50);
        AutoCrystalHack.lineWidth = new ValueNumber("Width", "Width", "The width for the outline.", 1.0f, 0.0f, 5.0f);
        AutoCrystalHack.fillColor = new ValueColor("FillColor", "FillColor", "The color for the filling.", new Color(0, 0, 255, 100));
        AutoCrystalHack.outlineColor = new ValueColor("OutlineColor", "OutlineColor", "The color for the outline.", new Color(0, 0, 255, 255));
    }
    
    public static class FadePosition
    {
        private final BlockPos position;
        private final long startTime;
        
        public FadePosition(final BlockPos position) {
            this.position = position;
            this.startTime = System.currentTimeMillis();
        }
        
        public BlockPos getPosition() {
            return this.position;
        }
        
        public long getStartTime() {
            return this.startTime;
        }
    }
    
    public enum Timings
    {
        Break, 
        Place;
    }
    
    public enum SwitchModes
    {
        None, 
        Normal, 
        Silent;
    }
    
    public enum Hands
    {
        None, 
        Mainhand, 
        Offhand;
    }
    
    public enum MultiPlaceModes
    {
        None, 
        Dynamic, 
        Static;
    }
    
    public enum RenderModes
    {
        None, 
        Normal, 
        Fade, 
        Size;
    }
    
    public enum Renders
    {
        None, 
        Normal;
    }
}
