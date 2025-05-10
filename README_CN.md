# Ultimate Scaler
[English](README.md) | **简体中文**

一个 Minecraft 模组，可以移除 30000000 处的世界边界，在更近的位置生成边境之地，并偏移和缩放地形生成。  

Forked from [PercyDan54/BorderRemover](https://github.com/PercyDan54/BorderRemover)  

[![](https://z3.ax1x.com/2021/08/02/fpgDCq.png)](https://www.curseforge.com/minecraft/mc-mods/fabric-api) [![](https://z3.ax1x.com/2021/08/02/fpgr80.png)](https://www.curseforge.com/minecraft/mc-mods/cloth-config)
![](https://s21.ax1x.com/2025/05/05/pEqlAhV.png)
## 设置

* `生成边境之地`：在更近的位置生成边境之地（第一层在 12550824）。  

* `生成碎片边境之地`：将柏林噪声生成器中的双精度数据类型更改为单精度数据类型，使地形更加破碎。  

* ~~`生成偏移`~~：偏移地形生成使用的 `ChunkPos`。设置为 `0` 禁用修改。  

    * 将方块坐标除以 16 得到区块坐标。  

    * 这个选项不推荐使用，因为它可能导致意外的问题。  

* `【OldBlendedNoise】X/Y/Z 坐标比例`：修改 `minecraft:OldBlendedNoise` 密度函数使用的 Y / XZ 坐标比例。设置为 `default` 禁用修改。  

    * 需要重新加载世界使更改生效。  

* `全局 X/Y/Z 缩放/偏移`：缩放或偏移大多数地形生成。

    * 这个选项目前只影响 Noise 和 OldBlendedNoise。  

    * 在未来，这个选项将影响几乎整个地形生成。  

## 调试屏幕

* 在调试屏幕中添加了一行`TerrainXYZ`，显示当前的地形生成位置。  
![](https://s21.ax1x.com/2025/05/05/pEq1jsg.png)

## 命令

* `locate pos`：使用二分法定位一个缩放和偏移过的新位置。  
    * 语法：`/locate pos <originalPos> <scale> <offset> [range]`  
    * `<originalPos>`：int/String，原本的 X/Y/Z 坐标。
    * `<scale>`：double，缩放因子，必须大于 0。
    * `<offset>`：double，偏移量。
    * `[range]`：double，搜索范围，默认为 `Double.MAX_VALUE / scale - offset`。
    * 示例：当没有偏移或缩放时，在 X/Z 轴的 607949781904244613165613056 处会生成一个特殊地形，我想知道当 X 轴的缩放为 4.5E8 且偏移量为 607949781904244603165613056 时该地形生成的位置，我可以使用 `/locate pos 607949781904244613165613056 450000000 607949781904244603165613056` 来获取新位置。此命令输出 77，这意味着缩放并偏移后此地形在 X 轴上的 77 处生成。
    * 这个命令在坐标很小时可能并不实用，但当坐标很大且受到精度损失时，这个命令非常有用。