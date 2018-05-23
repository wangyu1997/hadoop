//package cn.orcale.com.bigdata.hbase;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.HColumnDescriptor;
//import org.apache.hadoop.hbase.HTableDescriptor;
//import org.apache.hadoop.hbase.KeyValue;
//import org.apache.hadoop.hbase.MasterNotRunningException;
//import org.apache.hadoop.hbase.ZooKeeperConnectionException;
//import org.apache.hadoop.hbase.client.Delete;
//import org.apache.hadoop.hbase.client.Durability;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.HBaseAdmin;
//import org.apache.hadoop.hbase.client.HTable;
//import org.apache.hadoop.hbase.client.Put;
//import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.ResultScanner;
//import org.apache.hadoop.hbase.client.Scan;
//import org.apache.hadoop.hbase.util.Bytes;
//import org.apache.log4j.Logger;
///**
// *
// * ClassName: HbaseUtil
// * @Description: Hbase交互工具类
// * @author 余辉
// * @date 2017-3-18
// */
//public class HbaseDao {
//
//	public static Logger log = Logger.getLogger(HbaseDao.class);
//
//     public static Configuration configuration;
//     static {
//         configuration = HBaseConfiguration.create();
//         configuration.set("hbase.zookeeper.quorum", "tagtic-master,tagtic-slave02,tagtic-slave03");
//         configuration.set("hbase.zookeeper.property.clientPort", "2181");
//     }
//
//     public static void main(String[] args) {
//		 createTable("YH_BigData");
////    	 dropTable("YH_BigData");
////
////		 insertData("xiaoming" , "YH_BigData");
////
////		 QueryAll("YH_BigData");
////		 QueryByCondition1("xiaohui",  "YH_BigData");
////		 QueryByCondition1("xiaoming",  "YH_BigData");
////
////		deleteRow("YH_BigData","xiaoming");
//
//	}
//
//	/**
//	 * 创建表
//	 * @param tableName
//	 */
//	public static void createTable(String tableName) {
//		log.info("start create table ......");
//		try {
//			HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);
//			if (hBaseAdmin.tableExists(tableName)) {// 如果存在要创建的表，那么先删除，再创建
//				hBaseAdmin.disableTable(tableName);
//				hBaseAdmin.deleteTable(tableName);
//				log.info("-------------->"+tableName + " is exist,detele....");
//			}
//			HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
//			tableDescriptor.addFamily(new HColumnDescriptor("column1"));
//			tableDescriptor.addFamily(new HColumnDescriptor("column2"));
//			tableDescriptor.addFamily(new HColumnDescriptor("column3"));
//			hBaseAdmin.createTable(tableDescriptor);
//
//		} catch (MasterNotRunningException e) {
//			e.printStackTrace();
//		} catch (ZooKeeperConnectionException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		log.info("end create table ......");
//	}
//
//	/**
//	 * 插入数据
//	 * @param tableName
//	 */
//	private static void insertData(String rowkey , String tableName) {
//		try {
//			HTable ht = new HTable(configuration, tableName);
//
//			//RowKey
//			Put put = new Put(rowkey.getBytes());
//
//			//          列族                                        列名           值
//			put.add(Bytes.toBytes("column2"), "screen_width".getBytes(), "1080".getBytes());// 本行数据的第一列
//			put.add(Bytes.toBytes("column2"), "screen_height".getBytes(), "1920".getBytes());// 本行数据的第三列
//			put.add(Bytes.toBytes("column2"), "url".getBytes(), "www.baidu.com".getBytes());// 本行数据的第三列
//			put.add(Bytes.toBytes("column2"), "event_data".getBytes(), "12|16|13|17|12|16".getBytes());// 本行数据的第四列
//
//			put.setDurability(Durability.SYNC_WAL);
//
//			//put a record
//			ht.put(put);
//			ht.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//
//
//		}
//
//	/**
//	 * 删除一张表
//	 * @param tableName
//	 */
//	public static void dropTable(String tableName) {
//		try {
//			HBaseAdmin admin = new HBaseAdmin(configuration);
//			admin.disableTable(tableName);
//			admin.deleteTable(tableName);
//			log.info("-------------->"+tableName + " is exist,detele....");
//		} catch (MasterNotRunningException e) {
//			e.printStackTrace();
//		} catch (ZooKeeperConnectionException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//	/**
//	 * 根据 rowkey删除一条记录
//	 * @param tablename
//	 * @param rowkey
//	 */
//	 public static void deleteRow(String tablename, String rowkey)  {
//		try {
//			HTable table = new HTable(configuration, tablename);
//			List list = new ArrayList();
//			Delete d1 = new Delete(rowkey.getBytes());
//			list.add(d1);
//
//			table.delete(list);
//			System.out.println("-------------->"+"删除行成功!");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//
//	}
//
//
//	/**
//	 * 查询所有数据
//	 * @param tableName
//	 */
//	public static void QueryAll(String tableName) {
//		try {
//		HTable table = new HTable(configuration, tableName);
//
//			ResultScanner rs = table.getScanner(new Scan());
//			for (Result r : rs) {
//				System.out.println("获得到rowkey:" + new String(r.getRow()));
//				for (KeyValue keyValue : r.raw()) {
//					System.out.println("列：" + new String(keyValue.getFamily())
//							+ "====值:" + new String(keyValue.getValue()));
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 单条件查询,根据rowkey查询唯一一条记录
//	 * @param tableName
//	 */
//	public static void QueryByCondition1(String rowkey , String tableName) {
//
//		try {
//		HTable table = new HTable(configuration, tableName);
//			Get scan = new Get(rowkey.getBytes());// 根据rowkey查询
//			Result r = table.get(scan);
//			System.out.println("获得到rowkey:" + new String(r.getRow()));
//			for (KeyValue keyValue : r.raw()) {
//				System.out.println("列：" + new String(keyValue.getFamily())
//						+ "====值:" + new String(keyValue.getValue()));
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//}