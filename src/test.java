import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;



public class test {
	public static int numOfScheme=0;
	public static int costOfMinShceme=9999;
	public static int numOfCostMinShceme=-1;
	public static Stack<Object[]> CostMinShceme = new Stack<Object[]>();
	public static  int[][] buildRelation;


	/* 临时保存路径节点的栈 */
	public static int numOfAllNode=6;
	public static Stack<Node> stack = new Stack<Node>();
	/* 存储路径的集合 */
	public static ArrayList<Object[]> sers = new ArrayList<Object[]>();
	public static ArrayList<Object[]> ringPath = new ArrayList<Object[]>();
	public static Stack<Object[]> ringPathstack = new Stack<Object[]>();
	
    public  static Node[] nodeData;     //存储所有节点


	


	/* 判断节点是否在栈中 */
	public static boolean isNodeInStack(Node node)
	{
		Iterator<Node> it = stack.iterator();
		while (it.hasNext()) {
			Node node1 = (Node) it.next();
			if (node == node1)
				return true;
		}
		return false;
	}
	
		

	public static boolean isLinkInStack(Node a, Node z) {
		
		return true;
	}
	/* 此时栈中的节点组成一条所求路径，转储并打印输出 */
	public static void showAndSavePath()
	{
		Object[] o = stack.toArray();
		for (int i = 0; i < o.length; i++) {
			Node nNode = (Node) o[i];
			
			if(i < (o.length - 1))
				System.out.print(nNode.getName() + "->");
			else
				System.out.print(nNode.getName());
		}
		sers.add(o); /* 转储 */
		System.out.println("\n");
	}
	
	

	public static void ChooseRingPath(ArrayList<Object[]> arrayList) {
		int numOfnode = 0 ;  //环上节点的数量
		Node lastnode = null;
		for (int i =0;i < arrayList.size();i++ ) {
			Node cnode = (Node)arrayList.get(i)[0];
			if (cnode != lastnode) {
				lastnode = cnode;
				numOfnode++;
				
			}
			
		}
		int headOfPoint[] = new int[numOfnode];
		lastnode = null;
		for (int i =0, numOfPoint=0;i < arrayList.size();i++ ) {
			Node cnode = (Node)arrayList.get(i)[0];
			if (cnode != lastnode) {
				lastnode = cnode;
				headOfPoint[numOfPoint] = i;
				numOfPoint++;
			}
			
		}
		
		Recursion (0, headOfPoint, numOfnode);
		
		
	}
	
	public static boolean isDuplicateFiber() {
		
		int [][] fiberOcu = new int [numOfAllNode][numOfAllNode] ;
//		for(int i = 0; i<numOfAllNode;i++)
//			for (int j = 0; j<numOfAllNode;j++)
//				fiberOcu[i][j]=0;
		
		
		for(int i =0; i <ringPathstack.size();i++) {
			Object[] cFiberLink = ringPathstack.get(i);
			for(int j =0;j<cFiberLink.length-1;j++) {
				Node A = (Node)cFiberLink[j];
				Node Z = (Node)cFiberLink[j+1];
				int numOfA = A.getId();
				int numOfZ = Z.getId();
				fiberOcu[numOfA][numOfZ]++;
				fiberOcu[numOfZ][numOfA]++;
				if(fiberOcu[numOfA][numOfZ] >1 | fiberOcu[numOfZ][numOfA] >1) {
					
					return false;
				}

			}
		}
		return true;
		
	}
	
	public static void Recursion (int layer,int[] headOfPoint, int numOfNode) {
		int cpoint = headOfPoint[layer];
		while(cpoint <sers.size() ) {
			
			if(layer== (numOfNode-1))
				;
			else if(cpoint == headOfPoint[layer+1])
				break;
			
			ringPathstack.push(sers.get(cpoint));
			
			if(isDuplicateFiber()) {
				if (ringPathstack.size()<numOfNode) {
					int count = 0;
					for(int i =0;i<ringPathstack.size();i++){
						Object [] o = ringPathstack.get(i);
						for (int j = 0; j < o.length; j++) {
							count++;
							Node nNode = (Node) o[j];

							if (j<o.length-1) {
								Node zNode = (Node) o[(j+1)];
								int aNodeID = nNode.getId();
								int zNodeID = zNode.getId();
								if(buildRelation[aNodeID][zNodeID]==1) 
									count= count + 1000;
							}					
						}
					}
					if(count < costOfMinShceme)
					Recursion(layer+1,headOfPoint,numOfNode);

				}
				else {
					boolean isDuplicate = isDuplicateFiber();
					if (isDuplicate == true) {
						showRingRoute(numOfNode);
					}

				}
			}
			else {
				;
		
			}
				
			ringPathstack.pop();
			cpoint++;
		}
		
	}
	
	
	public static void showRingRoute(int numOfNode) {
		// TODO Auto-generated method stub
		numOfScheme++;
		System.out.println("可选的路由("+numOfScheme+"):");
		int count=0;
		
		for(int i =0;i<ringPathstack.size();i++){
			Object [] o = ringPathstack.get(i);
			for (int j = 0; j < o.length; j++) {


				count++;
				Node nNode = (Node) o[j];

				if (j<o.length-1) {
					Node zNode = (Node) o[(j+1)];
					int aNodeID = nNode.getId();
					int zNodeID = zNode.getId();
					if(buildRelation[aNodeID][zNodeID]==1) 
						count= count + 1000;
				}
			
				
				if( j < (o.length - 1))
					System.out.print(nNode.getName() + "->");
				else
					System.out.print(nNode.getName());
			}
			System.out.println("\n");



		}
		
		if(count<costOfMinShceme) {
			costOfMinShceme = count;
			numOfCostMinShceme = numOfScheme;
			CostMinShceme.clear();
			CostMinShceme.addAll(ringPathstack);   
		}	
		System.out.println("路由评价："+count+", 当前最优路由：第"+numOfCostMinShceme+"条（"+costOfMinShceme+")");
		System.out.println("\n");

		
		

		
	}
	
	public static void showRecRingRoute() {
		// TODO Auto-generated method stub
		
		System.out.println("\n");

		System.out.println("推荐的路由("+numOfCostMinShceme+"):");
		for(int i =0;i<CostMinShceme.size();i++){
			Object [] o = CostMinShceme.get(i);
			for (int j = 0; j < o.length; j++) {
				Node nNode = (Node) o[j];
				
				if( j < (o.length - 1))
					System.out.print(nNode.getName() + "->");
				else
					System.out.print(nNode.getName());
			}
			System.out.println("\n");
			



		}
		System.out.println("路由评价："+costOfMinShceme);
		System.out.println("规划新增的光缆：");	
		for(int i =0;i<CostMinShceme.size();i++){
			Object [] o = CostMinShceme.get(i);
			for (int j = 0; j < o.length-1; j++) {
				Node nNode = (Node) o[j];
				Node zNode = (Node) o[(j+1)];
				int aNodeID = nNode.getId();
				int zNodeID = zNode.getId();
				if(buildRelation[aNodeID][zNodeID]==1) {
					System.out.print(nNode.getName() + "->");
					System.out.println(zNode.getName());


				}
			}

		}
		

		System.out.println("\n");
		
		



		
		

		
	}

	//public static void 
	/*
	 * 寻找路径的方法 
	 * cNode: 当前的起始节点currentNode
	 * pNode: 当前起始节点的上一节点previousNode
	 * sNode: 最初的起始节点startNode
	 * eNode: 终点endNode
	 */
	public static boolean getPaths(Node cNode, Node pNode, Node sNode, Node eNode) {
		Node nNode = null;
		/* 如果符合条件判断说明出现环路，不能再顺着该路径继续寻路，返回false */
		if (cNode != null && pNode != null && cNode == pNode)
			return false;

		if (cNode != null) {
			int i = 0;
			/* 起始节点入栈 */
			stack.push(cNode);

			/* 如果该起始节点就是终点，说明找到一条路径 */
			if (cNode == eNode)
			{
				/* 转储并打印输出该路径，返回true */
				showAndSavePath();
				return true;
			}

			
			/* 如果不是,继续寻路 */
			else if (stack.size()>5) {           //实现跳数限制，5表示跳数
				stack.pop();
				return false;
			}
			else
			{
				/* 
				 * 从与当前起始节点cNode有连接关系的节点集中按顺序遍历得到一个节点
				 * 作为下一次递归寻路时的起始节点 
				 */
				nNode = cNode.getRelationNodes().get(i);   //找到第i个邻接节点
				while (nNode != null) {
					/*
					 * 如果nNode是最初的起始节点或者nNode就是cNode的上一节点或者nNode已经在栈中 ， 
					 * 说明产生环路 ，应重新在与当前起始节点有连接关系的节点集中寻找nNode
					 */
					if (pNode != null
							&& (nNode == sNode || nNode == pNode || isNodeInStack(nNode))) {
						i++;
						if (i >= cNode.getRelationNodes().size())
							nNode = null;
						else
							nNode = cNode.getRelationNodes().get(i);
						continue;
					}
					/* 以nNode为新的起始节点，当前起始节点cNode为上一节点，递归调用寻路方法 */
					if (getPaths(nNode, cNode, sNode, eNode))/* 递归调用 */
					{
						/* 如果找到一条路径，则弹出栈顶节点 */
						stack.pop();
					}
					/* 继续在与cNode有连接关系的节点集中测试nNode */
					i++;
					if (i >= cNode.getRelationNodes().size())
						nNode = null;
					else
						nNode = cNode.getRelationNodes().get(i);
				}
				/* 
				 * 当遍历完所有与cNode有连接关系的节点后，
				 * 说明在以cNode为起始节点到终点的路径已经全部找到 
				 */
				stack.pop();
				return false;
			}
		} else
			return false;
	}
	
	public static void getBuildRelation() {
		
	}
	
	
	public static int[][] newLink(int[][] curNodeRelation, Node[] ringNode, int type, int layer) {
	/*
	 * type 1为从环网节点机房上新建光缆
	 * type 2为从任意节点上新建光缆
	 * numOfAllNode 节点的总数量
	 * layer 控制距离
	 * 
	 */
		if(type == 1) {
			for(int i=0;i<ringNode.length;i++) {
				int ringNodeID = ringNode[i].getId();
				
				double mincost = Integer.MAX_VALUE;
				int mincostNodeID = Integer.MAX_VALUE;
				for(int j =0;j<curNodeRelation[ringNodeID].length;j++) {
					
					if(curNodeRelation[ringNodeID][j]==0 && ringNodeID !=j) {
						Node visitedNode = Node.allNode.get(j);
						double cost = Math.pow(ringNode[i].getLatitude()-visitedNode.getLatitude(), 2) 
								+ Math.pow(ringNode[i].getLongitude()-visitedNode.getLongitude(),2);
						
						if(cost<mincost) {
							mincostNodeID = j;
							mincost = cost;

						}
					}			
					
				}
				
				curNodeRelation[ringNodeID][mincostNodeID] = 1;
				curNodeRelation[mincostNodeID][ringNodeID] = 1;
				buildRelation[ringNodeID][mincostNodeID] = 1;
				buildRelation[mincostNodeID][ringNodeID] = 1;

				
			}			
			return curNodeRelation;
		}
		else if (type == 2) {
			
			costOfMinShceme = 4000; 
			for(int i=0;i<numOfAllNode;i++) {
				if(Node.allNode.get(i).getLatitude() == 0)
					continue;
				int theNodeID = i;
				
				for(int j =0;j<curNodeRelation[theNodeID].length;j++) {
					
					if(curNodeRelation[theNodeID][j]==0 && theNodeID !=j 
							&& Node.allNode.get(j).getLatitude() != 0) {

						Node visitedNode = Node.allNode.get(j);
			//			double cost = Math.pow(Node.allNode.get(i).getLatitude()-visitedNode.getLatitude(), 2) 
			//					+ Math.pow(Node.allNode.get(i).getLongitude()-visitedNode.getLongitude(),2);
						
						double cost = Math.pow(Node.allNode.get(i).getLatitude()-visitedNode.getLatitude(), 2) 
									+ Math.pow(Node.allNode.get(i).getLongitude()-visitedNode.getLongitude(),2);
						if(cost * 100000 < 2*layer - 1.7) {
							curNodeRelation[i][j] = 1;
							curNodeRelation[j][i] = 1;
							buildRelation[i][j] = 1;
							buildRelation[j][i] = 1;

						}
					}			
					
				}
				


				
			}			
			return curNodeRelation;
		}
		else 
			return null;

				
	}
	
	public static void clearNodeRelation() {
		for(int i =0;i< Node.allNode.size(); i++) {
			Node.allNode.get(i).relationNodes.clear();
		}
		
	
	}

	public static void main(String[] args)  {
		
	
		long sysDateBegin = System.currentTimeMillis();
		
		
		

		System.out.println("数据导入中...");

	    NodeDataBase node1=new NodeDataBase();
	    node1.inputNode("测试数据.xls");          //导入所有节点
	    
	    numOfAllNode = Node.allNode.size();
	    
		


	    
	    
	    
		/* 定义节点关系 */
	    
	    LinkDataBase linkdatabase = new LinkDataBase();
	    int[][] nodeRalation = linkdatabase.inputFiberLink("测试数据.xls");  //导入所有链路，并对邻接矩阵赋值
	    
	    


		
		
		
		/* 定义节点数组 */
		Node[] node = new Node[numOfAllNode];
		for(int i = 0;i<Node.allNode.size();i++) {
			node[i]=Node.allNode.get(i);       //将链表转成数组，为了符合后面算法的变量格式
		}
		
		
	
		
		/* 定义与节点相关联的节点集合 */
		for(int i=0;i<nodeRalation.length;i++)
		{
			ArrayList<Node> List = new ArrayList<Node>();
			
			for(int j=0;j<nodeRalation[i].length;j++)
			{
				
				if(nodeRalation[i][j] != 0)
					List.add(node[j]);
			}
			node[i].setRelationNodes(List);
			List = null;  //释放内存
		}

		/* 开始搜索所有路径  接入环112  
		 * * /
		 */
		
		/*
		int nodecount = 6;        //088
		Node[] ringNode = new Node[nodecount];
		ringNode[0] = node[489];
		ringNode[1] = node [218];
		ringNode[2] = node[125];
		ringNode[3] = node[1506];
		ringNode[4] = node[117];
		ringNode[5] = node[2534];
		*/
		
		/*
		int nodecount = 7;        //049
		Node[] ringNode = new Node[nodecount];
		ringNode[0] = node[489];
		ringNode[1] = node [44];
		ringNode[2] = node[278];
		ringNode[3] = node[2840];
		ringNode[4] = node[1142];
		ringNode[5] = node[283];
		ringNode[6] = node[2641];
		*/
		
		/*
		int nodecount = 9;            //312
		Node[] ringNode = new Node[nodecount];
		ringNode[0] = node[2484];   //郡江国际
		ringNode[1] = node [1210];  //理工科技园
		ringNode[2] = node[1609];  //上海电气都市工业园
		ringNode[3] = node[132];   //版权中心大厦(顶楼)
		ringNode[4] = node[467];   //丰泰世纪公寓(A楼1单元2F)
		ringNode[5] = node[2585];  //音像大厦
		ringNode[6] = node[1647];  //国际设计交流中心
		ringNode[7] = node[1283];  //龙泽大厦
		ringNode[8] = node[418];   //东方渔人码头
		*/
		
		/*
		int nodecount = 9;            //255
		Node[] ringNode = new Node[nodecount];
		ringNode[0] = node[489];   //复旦
		ringNode[1] = node [487];  //复城国际大厦
		ringNode[2] = node[1158];  //科技创业大厦
		ringNode[3] = node[1497];   //荣振大厦
		ringNode[4] = node[526];   //高新科技孵化基地
		ringNode[5] = node[832];  //沪东金融大厦(10F)
		ringNode[6] = node[1741];  //品牌中心
		ringNode[7] = node[2180];  //同济逸仙
		ringNode[8] = node[1527];   //三九
		 */
		 
		 
		
		/*
		
		int nodecount = 9;            //112
		Node[] ringNode = new Node[nodecount];
		ringNode[0] = node[489];
		ringNode[1] = node [2257];
		ringNode[2] = node[2354];
		ringNode[3] = node[1199];
		ringNode[4] = node[200];
		ringNode[5] = node[216];
		ringNode[6] = node[466];
		ringNode[7] = node[1305];
		ringNode[8] = node[411];
    */
		
		int nodecount = args.length;           //input
		Node[] ringNode = new Node[nodecount];
		for(int i = 0 ; i < nodecount; i++) {
			ringNode[i] = node [Integer.parseInt(args[i])];
		}
		
		
		/*
		int nodecount = 8;     //028
		ringNode[0] = node[489];
		ringNode[1] = node [1049];
		ringNode[2] = node[504];
		ringNode[3] = node[2253];
		ringNode[4] = node[2345];
		ringNode[5] = node[1821];
		ringNode[6] = node[2245];
		ringNode[7] = node[2343];
		
		*/
		
		
		for(int i =0; i<ringNode.length; i++) {
			getPaths(ringNode[i],null,ringNode[i],ringNode[(i+1)%nodecount]);
			
		}

		/*
		getPaths(node[489], null, node[489], node[218]);
		getPaths(node[218], null, node[218], node[125]);
		
		getPaths(node[125], null, node[125], node[1506]);
		getPaths(node[1506], null, node[1506], node[119]);
		getPaths(node[119], null, node[119], node[2534]);
		getPaths(node[2534], null, node[2534], node[489]);

		************************************************************
		
		getPaths(node[1049], null, node[1049], node[504]);
		getPaths(node[504], null, node[504], node[2253]);
		getPaths(node[2253], null, node[2253], node[2345]);
		getPaths(node[2345], null, node[2345], node[1821]);
		getPaths(node[1821], null, node[1821], node[2245]);
		getPaths(node[2245], null, node[2245], node[2343]);
		getPaths(node[2343], null, node[2343], node[489]);
 */


		
	ChooseRingPath(sers);
	if(numOfScheme > 0 ) 
		showRecRingRoute();
	else {
		
		
		int layer = 1;
		
		
		while(numOfScheme==0 && layer <=10) {
			System.out.println("****************************************************");

			System.out.println("当前资源下无解，启动基于地理信息（经纬度）的光缆新建规划！");
			System.out.println("当前地理范围为: "+layer);
			try{
				Thread.sleep(2000);
				}catch(Exception e){
				System.exit(0);//退出程序
				}
			
			nodeRalation = newLink(nodeRalation, ringNode, 1, layer );
			test.sers.clear();
			test.stack.clear();
			test.ringPathstack.clear();
			
			clearNodeRelation();
			
			for(int i=0;i<nodeRalation.length;i++)
			{
				ArrayList<Node> List = new ArrayList<Node>();
				
				for(int j=0;j<nodeRalation[i].length;j++)
				{
					
					if(nodeRalation[i][j] != 0)
						List.add(node[j]);
				}
				node[i].setRelationNodes(List);
				List = null;  //释放内存
			}
			for(int i =0; i<ringNode.length; i++) {
				getPaths(ringNode[i],null,ringNode[i],ringNode[(i+1)%nodecount]);
			}
			
			ChooseRingPath(sers);
			if(numOfScheme > 0 ) 
				showRecRingRoute();

			
			
			layer++;

		}
		
		

		
		
	}
	
	long sysDateEnd = System.currentTimeMillis();

	System.out.println("计算耗时:"+(sysDateEnd-sysDateBegin)/60000+"分"+((sysDateEnd-sysDateBegin)/1000)%60+"秒");


	}
}