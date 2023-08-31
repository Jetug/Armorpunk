package com.jetug.chassis_addon.client.utils;

import com.mojang.math.Vector3d;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class Pos3D extends Vec3 {

    public Pos3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Pos3D(Vec3 vec) {
        this(vec.x, vec.y, vec.z);
    }

    public Pos3D(Vector3d vec) {
        this(vec.x, vec.y, vec.z);
    }

    public Pos3D translate(double x, double y, double z) {
        return new Pos3D(this.x + x, this.y + y, this.z + z);
    }

    public Pos3D translate(Vec3 pos) {
        return translate(pos.x, pos.y, pos.z);
    }

    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Pos3D clone() {
        return new Pos3D(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vec3 && ((Vec3) obj).x == x && ((Vec3) obj).y == y && ((Vec3) obj).z == z;
    }
}
