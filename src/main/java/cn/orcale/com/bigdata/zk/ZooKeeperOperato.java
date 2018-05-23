package cn.orcale.com.bigdata.zk;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
  
/***
 * 
 * <p>Title:</p>
 * <p>Desc :</p>
 * @author yuhui
 * @date 2017年3月1日 下午9:10:23
 */
public class ZooKeeperOperato extends AbstractZooKeeper {  
      
    private static Log log = LogFactory.getLog(ZooKeeperOperato.class.getName());  
       
    
    public void create(String path,byte[] data)throws KeeperException, InterruptedException{  
    	
    	Stat exists = this.pathExists(path);
    	if(exists == null){
    		this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT); 
    		System.out.println(path +" 创建成功");
    	}
         
    }  

    public Stat pathExists(String Path) throws KeeperException, InterruptedException{  
        Stat stat = null;       
        stat = zooKeeper.exists(Path, false);      
        return  stat;

    }  
    
    
    public void getChild(String path) throws KeeperException, InterruptedException{     
        try{  
            List<String> list=this.zooKeeper.getChildren(path, false);  
            if(list.isEmpty()){  
                System.out.println(path+"中没有节点");  
            }else{  
            	System.out.println(path+"中存在节点");  
                for(String child:list){  
                	System.out.println("节点为："+child);  
                }  
            }  
        }catch (KeeperException.NoNodeException e) {  
            // TODO: handle exception  
             throw e;     
  
        }  
    }  
      
    public byte[] getData(String path) throws KeeperException, InterruptedException {     
        return  this.zooKeeper.getData(path, false,null);     
    }    
      
    
    public void deletePath(String path) throws KeeperException, InterruptedException{  
     
           zooKeeper.delete(path, -1);  
           System.out.println( path + "删除成功");
   } 
           
    
     public static void main(String[] args) {  
    	  	     	
    	 
         try {     
                ZooKeeperOperato zkoperator = new ZooKeeperOperato();     
                zkoperator.connect("192.168.200.11");  
                  
                byte[] data = new byte[]{'a','b','c','d'};                                      
                
//              zkoperator.create("/xiaohui",null);
//                zkoperator.deletePath("/xiaohui");
                   
//                zkoperator.create("/xiaohui/child1",data);     
//                System.out.println((Arrays.toString(zkoperator.getData("/xiaohui/child1"))));     
//                   
//              zkoperator.create("/xiaohui/child2",data);     
//              System.out.println(Arrays.toString(zkoperator.getData("/xiaohui/child2")));     
                 

//               System.out.println("节点孩子信息:");     
//                zkoperator.getChild("/xiaohui");     
                               
               zkoperator.close();     
                  
                  
            } catch (Exception e) {     
                e.printStackTrace();     
            }     
  
    }  
}  
