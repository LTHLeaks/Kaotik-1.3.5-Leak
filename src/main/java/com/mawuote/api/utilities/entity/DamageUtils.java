package com.mawuote.api.utilities.entity;

import com.mawuote.api.utilities.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;

public class DamageUtils implements IMinecraft
{
    public static float calculateDamage(final double posX, final double posY, final double posZ, final EntityLivingBase entity) {
        try {
            final double distance = entity.func_70011_f(posX, posY, posZ) / 12.0;
            final double value = (1.0 - distance) * DamageUtils.mc.field_71441_e.func_72842_a(new Vec3d(posX, posY, posZ), entity.func_174813_aQ());
            float damage = (int)((value * value + value) / 2.0 * 7.0 * 12.0 + 1.0) * ((DamageUtils.mc.field_71441_e.func_175659_aa().func_151525_a() == 0) ? 0.0f : ((DamageUtils.mc.field_71441_e.func_175659_aa().func_151525_a() == 2) ? 1.0f : ((DamageUtils.mc.field_71441_e.func_175659_aa().func_151525_a() == 1) ? 0.5f : 1.5f)));
            damage = CombatRules.func_189427_a(damage, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
            damage *= 1.0f - MathHelper.func_76131_a((float)EnchantmentHelper.func_77508_a(entity.func_184193_aE(), DamageSource.func_94539_a(new Explosion((World)DamageUtils.mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0f, false, true))), 0.0f, 20.0f) / 25.0f;
            if (entity.func_70644_a((Potion)Objects.requireNonNull(Potion.func_188412_a(11)))) {
                damage -= damage / 4.0f;
            }
            return damage;
        }
        catch (final NullPointerException | ConcurrentModificationException exception) {
            return 0.0f;
        }
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = DamageUtils.mc.field_71441_e.func_175659_aa().func_151525_a();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.func_94539_a(explosion);
            damage = CombatRules.func_189427_a(damage, (float)ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
            int k = 0;
            try {
                k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), ds);
            }
            catch (final Exception ex) {}
            final float f = MathHelper.func_76131_a((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.func_70644_a(MobEffects.field_76429_m)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.func_189427_a(damage, (float)entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
        return damage;
    }
    
    public static boolean shouldBreakArmor(final EntityLivingBase entity, final float targetPercent) {
        for (final ItemStack stack : entity.func_184193_aE()) {
            if (stack == null || stack.func_77973_b() == Items.field_190931_a) {
                return true;
            }
            final float armorPercent = (stack.func_77958_k() - stack.func_77952_i()) / (float)stack.func_77958_k() * 100.0f;
            if (targetPercent >= armorPercent && stack.field_77994_a < 2) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.func_77973_b();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)((stack.func_77958_k() - stack.func_77952_i()) / (float)stack.func_77958_k() * 100.0f);
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.func_70011_f(posX, posY, posZ) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
        }
        catch (final Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)DamageUtils.mc.field_71441_e, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float calculateDamage(final Entity crystal, final Entity entity) {
        return calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity);
    }
    
    public static float calculateDamage(final BlockPos pos, final Entity entity) {
        return calculateDamage(pos.func_177958_n() + 0.5, pos.func_177956_o() + 1, pos.func_177952_p() + 0.5, entity);
    }
}
