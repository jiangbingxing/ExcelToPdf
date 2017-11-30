# ExeclToPdf
把Execl转为pdf完美版
/**
	 * 把EXECL的输入流转化为pdf的输入流
	 * 解决中文不显示问题
	 * 兼容2003和2007
	 * 这里因为项目需要，如果想生成pdf文件请把第128行代码删掉
	 * 	// 设置字体，因为itext中并没有中文字体，
	 //所以我们在Windows上找了个中文字体,加入到项目中
	 //这里我们需要在项目的resource下加入中文字体库
	 //我这里加入的是Windows下的SIMKAI.TTF字体库
