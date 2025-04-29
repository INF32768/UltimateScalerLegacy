package me.inf32768.ultimatescaler.mixins.options;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(GridWidget.class)
public interface IMixinGridWidget {
    @Accessor
    List<ClickableWidget> getChildren();
}
