package cn.hutool.core.io;

import java.io.File;
import java.io.FileFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import cn.hutool.core.io.file.LineSeparator;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;

/**
 * {@link FileUtil} 单元测试类
 * 
 * @author Looly
 */
public class FileUtilTest {

	public static List<String> reqIdList = Arrays.asList(
			"UAFYRZ2020030204160432930058",
			"UAFYRZ2020030204160443932379",
			"UAFYRZ2020030204160440931694",
			"UAFYRZ2020030204160455935211",
			"UAFYRZ2020030204160438931420",
			"UAFYRZ2020030204160438931428",
			"UAFYRZ2020030204160438931256",
			"UAFYRZ2020030204160438931264",
			"UAFYRZ2020030204160455935161",
			"UAFYRZ2020030204160454934841",
			"UAFYRZ2020030204160438931252",
			"UAFYRZ2020030204160440931734",
			"UAFYRZ2020030204160455935179",
			"UAFYRZ2020030204160455935187",
			"UAFYRZ2020030204160441931974",
			"UAFYRZ2020030204160454934869",
			"UAFYRZ2020030204160443932339",
			"UAFYRZ2020030204160443932357",
			"UAFYRZ2020030204160454934857",
			"UAFYRZ2020030204160456935406",
			"UAFYRZ2020030204160452934476",
			"UAFYRZ2020030204160448933592",
			"UAFYRZ2020030204160441931986",
			"UAFYRZ2020030204160437931127",
			"UAFYRZ2020030204160448933605",
			"UAFYRZ2020030204160441931999",
			"UAFYRZ2020030204160456935380",
			"UAFYRZ2020030204160456935384",
			"UAFYRZ2020030204160454934967",
			"UAFYRZ2020030204160454934968",
			"UAFYRZ2020030204160435930696",
			"UAFYRZ2020030204160435930698",
			"UAFYRZ2020030204160448933606",
			"UAFYRZ2020030204160433930392",
			"UAFYRZ2020030204160445932951"
	);

	@Test
	public void fileReadLinesTest(){
		File file = new File("E:/uaf-baihangcredit-provider.2020-03-02.log");
		//File file = new File("E:/1.txt");
		LineHandler lineHandler = new LineHandler() {
			@Override
			public void handle(String line) {
				for(String reqId : reqIdList){
					String temp = "d3ReportLoanInfo前端接口报文：{\"reqID\":\"" + reqId +"\"";
					if(line.contains(temp)){
						System.out.println(line.substring(line.indexOf("d3ReportLoanInfo前端接口报文：") + 23));
					}
				}
			}
		};
		FileUtil.readLines(file, StandardCharsets.UTF_8,lineHandler);
	}

	@Test(expected = IllegalArgumentException.class)
	public void fileTest() {
		File file = FileUtil.file("d:/aaa", "bbb");
		Assert.assertNotNull(file);

		// 构建目录中出现非子目录抛出异常
		FileUtil.file(file, "../ccc");
		
		FileUtil.file("E:/");
	}
	
	@Test
	public void getAbsolutePathTest() {
		String absolutePath = FileUtil.getAbsolutePath("LICENSE-junit.txt");
		Assert.assertNotNull(absolutePath);
		String absolutePath2 = FileUtil.getAbsolutePath(absolutePath);
		Assert.assertNotNull(absolutePath2);
		Assert.assertEquals(absolutePath, absolutePath2);
	}

	@Test
	public void getAbsolutePathTest2() {
		String path = FileUtil.getAbsolutePath("中文.xml");
		Assert.assertTrue(path.contains("中文.xml"));
	}

	@Test
	@Ignore
	public void touchTest() {
		FileUtil.touch("d:\\tea\\a.jpg");
	}

	@Test
	@Ignore
	public void delTest() {
		// 删除一个不存在的文件，应返回true
		boolean result = FileUtil.del("e:/Hutool_test_3434543533409843.txt");
		Assert.assertTrue(result);
	}
	
	@Test
	@Ignore
	public void delTest2() {
		// 删除一个不存在的文件，应返回true
		boolean result = FileUtil.del(Paths.get("e:/Hutool_test_3434543533409843.txt"));
		Assert.assertTrue(result);
	}

	@Test
	@Ignore
	public void renameTest() {
		FileUtil.rename(FileUtil.file("hutool.jpg"), "b.png", false, false);
	}

	@Test
	public void copyTest() {
		File srcFile = FileUtil.file("hutool.jpg");
		File destFile = FileUtil.file("hutool.copy.jpg");

		FileUtil.copy(srcFile, destFile, true);

		Assert.assertTrue(destFile.exists());
		Assert.assertEquals(srcFile.length(), destFile.length());
	}

	@Test
	@Ignore
	public void copyFilesFromDir() {
		File srcFile = FileUtil.file("D:\\驱动");
		File destFile = FileUtil.file("d:\\驱动备份");

		FileUtil.copyFilesFromDir(srcFile, destFile, true);
	}

	@Test
	public void equlasTest() {
		// 源文件和目标文件都不存在
		File srcFile = FileUtil.file("d:/hutool.jpg");
		File destFile = FileUtil.file("d:/hutool.jpg");

		boolean equals = FileUtil.equals(srcFile, destFile);
		Assert.assertTrue(equals);

		// 源文件存在，目标文件不存在
		File srcFile1 = FileUtil.file("hutool.jpg");
		File destFile1 = FileUtil.file("d:/hutool.jpg");

		boolean notEquals = FileUtil.equals(srcFile1, destFile1);
		Assert.assertFalse(notEquals);
	}

	@Test
	@Ignore
	public void convertLineSeparatorTest() {
		FileUtil.convertLineSeparator(FileUtil.file("d:/aaa.txt"), CharsetUtil.CHARSET_UTF_8, LineSeparator.WINDOWS);
	}

	@Test
	public void normalizeTest() {
		Assert.assertEquals("/foo/", FileUtil.normalize("/foo//"));
		Assert.assertEquals("/foo/", FileUtil.normalize("/foo/./"));
		Assert.assertEquals("/bar", FileUtil.normalize("/foo/../bar"));
		Assert.assertEquals("/bar/", FileUtil.normalize("/foo/../bar/"));
		Assert.assertEquals("/baz", FileUtil.normalize("/foo/../bar/../baz"));
		Assert.assertEquals("/", FileUtil.normalize("/../"));
		Assert.assertEquals("foo", FileUtil.normalize("foo/bar/.."));
		Assert.assertEquals("bar", FileUtil.normalize("foo/../../bar"));
		Assert.assertEquals("bar", FileUtil.normalize("foo/../bar"));
		Assert.assertEquals("/server/bar", FileUtil.normalize("//server/foo/../bar"));
		Assert.assertEquals("/bar", FileUtil.normalize("//server/../bar"));
		Assert.assertEquals("C:/bar", FileUtil.normalize("C:\\foo\\..\\bar"));
		Assert.assertEquals("C:/bar", FileUtil.normalize("C:\\..\\bar"));
		Assert.assertEquals("bar", FileUtil.normalize("../../bar"));
		Assert.assertEquals("C:/bar", FileUtil.normalize("/C:/bar"));

		Assert.assertEquals("\\/192.168.1.1/Share/", FileUtil.normalize("\\\\192.168.1.1\\Share\\"));
	}

	@Test
	public void normalizeHomePathTest() {
		String home = FileUtil.getUserHomePath().replace('\\', '/');
		Assert.assertEquals(home + "/bar/", FileUtil.normalize("~/foo/../bar/"));
	}

	@Test
	public void normalizeClassPathTest() {
		Assert.assertEquals("", FileUtil.normalize("classpath:"));
	}

	@Test
	public void doubleNormalizeTest() {
		String normalize = FileUtil.normalize("/aa/b:/c");
		String normalize2 = FileUtil.normalize(normalize);
		Assert.assertEquals("/aa/b:/c", normalize);
		Assert.assertEquals(normalize, normalize2);
	}

	@Test
	public void subPathTest() {
		Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

		Path subPath = FileUtil.subPath(path, 5, 4);
		Assert.assertEquals("eee", subPath.toString());
		subPath = FileUtil.subPath(path, 0, 1);
		Assert.assertEquals("aaa", subPath.toString());
		subPath = FileUtil.subPath(path, 1, 0);
		Assert.assertEquals("aaa", subPath.toString());

		// 负数
		subPath = FileUtil.subPath(path, -1, 0);
		Assert.assertEquals("aaa/bbb/ccc/ddd/eee", subPath.toString().replace('\\', '/'));
		subPath = FileUtil.subPath(path, -1, Integer.MAX_VALUE);
		Assert.assertEquals("fff", subPath.toString());
		subPath = FileUtil.subPath(path, -1, path.getNameCount());
		Assert.assertEquals("fff", subPath.toString());
		subPath = FileUtil.subPath(path, -2, -3);
		Assert.assertEquals("ddd", subPath.toString());
	}

	@Test
	public void subPathTest2() {
		String subPath = FileUtil.subPath("d:/aaa/bbb/", "d:/aaa/bbb/ccc/");
		Assert.assertEquals("ccc/", subPath);

		subPath = FileUtil.subPath("d:/aaa/bbb", "d:/aaa/bbb/ccc/");
		Assert.assertEquals("ccc/", subPath);

		subPath = FileUtil.subPath("d:/aaa/bbb", "d:/aaa/bbb/ccc/test.txt");
		Assert.assertEquals("ccc/test.txt", subPath);

		subPath = FileUtil.subPath("d:/aaa/bbb/", "d:/aaa/bbb/ccc");
		Assert.assertEquals("ccc", subPath);

		subPath = FileUtil.subPath("d:/aaa/bbb", "d:/aaa/bbb/ccc");
		Assert.assertEquals("ccc", subPath);

		subPath = FileUtil.subPath("d:/aaa/bbb", "d:/aaa/bbb");
		Assert.assertEquals("", subPath);

		subPath = FileUtil.subPath("d:/aaa/bbb/", "d:/aaa/bbb");
		Assert.assertEquals("", subPath);
	}

	@Test
	public void getPathEle() {
		Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

		Path ele = FileUtil.getPathEle(path, -1);
		Assert.assertEquals("fff", ele.toString());
		ele = FileUtil.getPathEle(path, 0);
		Assert.assertEquals("aaa", ele.toString());
		ele = FileUtil.getPathEle(path, -5);
		Assert.assertEquals("bbb", ele.toString());
		ele = FileUtil.getPathEle(path, -6);
		Assert.assertEquals("aaa", ele.toString());
	}

	@Test
	public void listFileNamesTest() {
		List<String> names = FileUtil.listFileNames("classpath:");
		Assert.assertTrue(names.contains("hutool.jpg"));

		names = FileUtil.listFileNames("");
		Assert.assertTrue(names.contains("hutool.jpg"));

		names = FileUtil.listFileNames(".");
		Assert.assertTrue(names.contains("hutool.jpg"));
	}
	
	@Test
	@Ignore
	public void listFileNamesTest2() {
		List<String> names = FileUtil.listFileNames("D:\\m2_repo\\commons-cli\\commons-cli\\1.0\\commons-cli-1.0.jar!org/apache/commons/cli/");
		for (String string : names) {
			Console.log(string);
		}
	}

	@Test
	public void loopFilesTest() {

		FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return FileUtil.readUtf8String(pathname).contains("addon-resizer");
			}
		};

		List<File> files = FileUtil.loopFiles("F:\\idea_projects\\myprojects\\kube-prometheus\\manifests\\setup", fileFilter);
		for (File file : files) {
			Console.log(file.getPath());
		}
	}
	
	@Test
	@Ignore
	public void loopFilesWithDepthTest() {
		List<File> files = FileUtil.loopFiles(FileUtil.file("d:/m2_repo"), 2, null);
		for (File file : files) {
			Console.log(file.getPath());
		}
	}

	@Test
	public void getParentTest() {
		// 只在Windows下测试
		if (FileUtil.isWindows()) {
			File parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 0);
			Assert.assertEquals(FileUtil.file("d:\\aaa\\bbb\\cc\\ddd"), parent);

			parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 1);
			Assert.assertEquals(FileUtil.file("d:\\aaa\\bbb\\cc"), parent);

			parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 2);
			Assert.assertEquals(FileUtil.file("d:\\aaa\\bbb"), parent);

			parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 4);
			Assert.assertEquals(FileUtil.file("d:\\"), parent);

			parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 5);
			Assert.assertNull(parent);

			parent = FileUtil.getParent(FileUtil.file("d:/aaa/bbb/cc/ddd"), 10);
			Assert.assertNull(parent);
		}
	}

	@Test
	public void lastIndexOfSeparatorTest() {
		String dir = "d:\\aaa\\bbb\\cc\\ddd";
		int index = FileUtil.lastIndexOfSeparator(dir);
		Assert.assertEquals(13, index);
		
		String file = "ddd.jpg";
		int index2 = FileUtil.lastIndexOfSeparator(file);
		Assert.assertEquals(-1, index2);
	}

	@Test
	public void getNameTest() {
		String path = "d:\\aaa\\bbb\\cc\\ddd\\";
		String name = FileUtil.getName(path);
		Assert.assertEquals("ddd", name);

		path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
		name = FileUtil.getName(path);
		Assert.assertEquals("ddd.jpg", name);
	}

	@Test
	public void mainNameTest() {
		String path = "d:\\aaa\\bbb\\cc\\ddd\\";
		String mainName = FileUtil.mainName(path);
		Assert.assertEquals("ddd", mainName);

		path = "d:\\aaa\\bbb\\cc\\ddd";
		mainName = FileUtil.mainName(path);
		Assert.assertEquals("ddd", mainName);

		path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
		mainName = FileUtil.mainName(path);
		Assert.assertEquals("ddd", mainName);
	}

	@Test
	public void extNameTest() {
		String path = "d:\\aaa\\bbb\\cc\\ddd\\";
		String mainName = FileUtil.extName(path);
		Assert.assertEquals("", mainName);

		path = "d:\\aaa\\bbb\\cc\\ddd";
		mainName = FileUtil.extName(path);
		Assert.assertEquals("", mainName);

		path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
		mainName = FileUtil.extName(path);
		Assert.assertEquals("jpg", mainName);
	}

	@Test
	public void getWebRootTest() {
		File webRoot = FileUtil.getWebRoot();
		Assert.assertNotNull(webRoot);
		Assert.assertEquals("hutool-core", webRoot.getName());
	}
	
	@Test
	public void getMimeTypeTest() {
		String mimeType = FileUtil.getMimeType("test2Write.jpg");
		Assert.assertEquals("image/jpeg", mimeType);
	}
}
