package alan.androidframe.projectutils;

import android.net.Uri;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CommonUtils
{
	public static boolean equals(Object o1, Object o2)
	{
		if (o1 == null && null == o2)
		{
			return true;
		}
		if (o1 != null && o2 != null)
		{
			return o1.equals(o2);
		}
		return false;
	}

	public static boolean hasItem(Collection<?> collection)
	{
		return collection != null && !collection.isEmpty();
	}

	/**
	 * 检测两个collection是否size相同
	 * 都为null表示相同，一个null一个size=0也相同
	 *
	 * @param collection1
	 * @param collection2
	 * @return
	 */
	public static boolean isSameSize(Collection<?> collection1, Collection<?> collection2)
	{
		if (!hasItem(collection1) && !hasItem(collection2))
		{
			return true;
		}
		else if (hasItem(collection1) && hasItem(collection2))
		{
			return collection1.size() == collection2.size();
		}
		else
		{
			return false;
		}
	}

	public static int parseInt(String str, int defaultValue)
	{
		try
		{
			return Integer.valueOf(str);
		}
		catch (Exception e)
		{
			LogUtils.e(e);
			return defaultValue;
		}
	}

	public static boolean checkNullEquals(Object obj1, Object obj2)
	{
		if (obj1 == null && null == obj2)
		{
			return true;
		}
		if (obj1 != null && obj2 != null)
		{
			return obj1.equals(obj2);
		}
		return false;
	}

	public static int getCollectionSize(Collection collection)
	{
		return collection == null ? 0 : collection.size();
	}

	public static boolean checkPosOk(int pos, int size)
	{
		return pos >= 0 && pos < size;
	}

	public static <T> T getListFirstItem(List<T> list)
	{
		return safeGetListItem(0, list);
	}

	public static <T> T safeGetListItem(int index, List<T> list)
	{
		if (CommonUtils.getCollectionSize(list) > index && index >= 0)
		{
			return list.get(index);
		}
		return null;
	}

	public static <T> T getListLastItem(List<T> list)
	{
		final int size = getCollectionSize(list);
		if (size > 0)
		{
			return list.get(size - 1);
		}
		return null;
	}

	public static boolean isTypeEquals(Object obj1, Object obj2)
	{
		if (obj1 == null && obj2 == null)
		{
			return true;
		}
		if (obj1 != null && obj2 != null)
		{
			return obj1.getClass() == obj2.getClass();
		}
		return false;
	}

	/**
	 * 用于Compare函数中两个long值比较，两个long值直接相减然后强转为int会有问题
	 * 
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	public static int compareLong(long lhs, long rhs)
	{
		if (lhs > rhs)
		{
			return 1;
		}
		else if (lhs < rhs)
		{
			return -1;
		}
		return 0;
	}

	public static boolean isSameDay(Date date1, Date date2)
	{
		if (date1 == null && date2 == null)
		{
			return true;
		}
		if (date1 == null || date2 == null)
		{
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}

	public static boolean isListHasAllSameItems(List list1, List list2)
	{
		if (!CommonUtils.isSameSize(list1, list2))
		{
			return false;
		}
		final int SIZE = CommonUtils.getCollectionSize(list1);
		for (int i = 0; i < SIZE; i++)
		{
			if (!CommonUtils.equals(list1.get(i), list2.get(i)))
			{
				return false;
			}
		}
		return true;
	}
//
//	public static Uri safeGetUri(String path)
//	{
//		if (path == null)
//		{
//			return Uri.parse("");
//		}
//		if (FileUtils.isLocalPath(path))
//		{
//			File file = new File(FileUtils.getDecodeFilePath(path));
//			// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//			// {
//			// return
//			// FileProvider.getUriForFile(ActivityManager.Companion.getInstance().getApplicationContext(),
//			// "com.inspiration.lemoCard.fileprovider", file);
//			// }
//			// else
//			// {
//			return Uri.fromFile(file);
//			// }
//		}
//		return Uri.parse(path);
//	}

	public static <T> List<T> mergeList(List<T> list1, List<T> list2)
	{
		List<T> resultList = new ArrayList<>();
		if (list1 != null)
		{
			resultList.addAll(list1);
		}
		if (list2 != null)
		{
			resultList.addAll(list2);
		}
		return resultList;
	}

	public static List<String> getCopyStringList(List<String> list)
	{
		final int SIZE = CommonUtils.getCollectionSize(list);
		if (SIZE == 0)
		{
			return new ArrayList<>();
		}
		List<String> newList = new ArrayList<>(Arrays.asList(new String[SIZE]));
		Collections.copy(newList, list);
		return newList;
	}

	/**
	 *
	 * @param float1
	 * @param float2
	 * @return =float1-float2
	 */
	public static float subFloat(float float1, float float2)
	{
		BigDecimal totalFeeDecimal = new BigDecimal(Float.toString(float1));
		BigDecimal totalFeeAfterDecimal = new BigDecimal(Float.toString(float2));
		return totalFeeDecimal.subtract(totalFeeAfterDecimal).floatValue();
	}

	/**
	 *
	 * @param float1
	 * @param float2
	 * @return =float1+float2
	 */
	public static float addFloat(float float1, float float2)
	{
		BigDecimal totalFeeDecimal = new BigDecimal(Float.toString(float1));
		BigDecimal totalFeeAfterDecimal = new BigDecimal(Float.toString(float2));
		return totalFeeDecimal.add(totalFeeAfterDecimal).floatValue();
	}
}
