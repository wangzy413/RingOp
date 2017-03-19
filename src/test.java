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


	/* ��ʱ����·���ڵ��ջ */
	public static int numOfAllNode=6;
	public static Stack<Node> stack = new Stack<Node>();
	/* �洢·���ļ��� */
	public static ArrayList<Object[]> sers = new ArrayList<Object[]>();
	public static ArrayList<Object[]> ringPath = new ArrayList<Object[]>();
	public static Stack<Object[]> ringPathstack = new Stack<Object[]>();
	
    public  static Node[] nodeData;     //�洢���нڵ�


	


	/* �жϽڵ��Ƿ���ջ�� */
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
	/* ��ʱջ�еĽڵ����һ������·����ת������ӡ��� */
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
		sers.add(o); /* ת�� */
		System.out.println("\n");
	}
	
	

	public static void ChooseRingPath(ArrayList<Object[]> arrayList) {
		int numOfnode = 0 ;  //���Ͻڵ������
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
		System.out.println("��ѡ��·��("+numOfScheme+"):");
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
		System.out.println("·�����ۣ�"+count+", ��ǰ����·�ɣ���"+numOfCostMinShceme+"����"+costOfMinShceme+")");
		System.out.println("\n");

		
		

		
	}
	
	public static void showRecRingRoute() {
		// TODO Auto-generated method stub
		
		System.out.println("\n");

		System.out.println("�Ƽ���·��("+numOfCostMinShceme+"):");
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
		System.out.println("·�����ۣ�"+costOfMinShceme);
		System.out.println("�滮�����Ĺ��£�");	
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
	 * Ѱ��·���ķ��� 
	 * cNode: ��ǰ����ʼ�ڵ�currentNode
	 * pNode: ��ǰ��ʼ�ڵ����һ�ڵ�previousNode
	 * sNode: �������ʼ�ڵ�startNode
	 * eNode: �յ�endNode
	 */
	public static boolean getPaths(Node cNode, Node pNode, Node sNode, Node eNode) {
		Node nNode = null;
		/* ������������ж�˵�����ֻ�·��������˳�Ÿ�·������Ѱ·������false */
		if (cNode != null && pNode != null && cNode == pNode)
			return false;

		if (cNode != null) {
			int i = 0;
			/* ��ʼ�ڵ���ջ */
			stack.push(cNode);

			/* �������ʼ�ڵ�����յ㣬˵���ҵ�һ��·�� */
			if (cNode == eNode)
			{
				/* ת������ӡ�����·��������true */
				showAndSavePath();
				return true;
			}

			
			/* �������,����Ѱ· */
			else if (stack.size()>5) {           //ʵ���������ƣ�5��ʾ����
				stack.pop();
				return false;
			}
			else
			{
				/* 
				 * ���뵱ǰ��ʼ�ڵ�cNode�����ӹ�ϵ�Ľڵ㼯�а�˳������õ�һ���ڵ�
				 * ��Ϊ��һ�εݹ�Ѱ·ʱ����ʼ�ڵ� 
				 */
				nNode = cNode.getRelationNodes().get(i);   //�ҵ���i���ڽӽڵ�
				while (nNode != null) {
					/*
					 * ���nNode���������ʼ�ڵ����nNode����cNode����һ�ڵ����nNode�Ѿ���ջ�� �� 
					 * ˵��������· ��Ӧ�������뵱ǰ��ʼ�ڵ������ӹ�ϵ�Ľڵ㼯��Ѱ��nNode
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
					/* ��nNodeΪ�µ���ʼ�ڵ㣬��ǰ��ʼ�ڵ�cNodeΪ��һ�ڵ㣬�ݹ����Ѱ·���� */
					if (getPaths(nNode, cNode, sNode, eNode))/* �ݹ���� */
					{
						/* ����ҵ�һ��·�����򵯳�ջ���ڵ� */
						stack.pop();
					}
					/* ��������cNode�����ӹ�ϵ�Ľڵ㼯�в���nNode */
					i++;
					if (i >= cNode.getRelationNodes().size())
						nNode = null;
					else
						nNode = cNode.getRelationNodes().get(i);
				}
				/* 
				 * ��������������cNode�����ӹ�ϵ�Ľڵ��
				 * ˵������cNodeΪ��ʼ�ڵ㵽�յ��·���Ѿ�ȫ���ҵ� 
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
	 * type 1Ϊ�ӻ����ڵ�������½�����
	 * type 2Ϊ������ڵ����½�����
	 * numOfAllNode �ڵ��������
	 * layer ���ƾ���
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
		
		
		

		System.out.println("���ݵ�����...");

	    NodeDataBase node1=new NodeDataBase();
	    node1.inputNode("��������.xls");          //�������нڵ�
	    
	    numOfAllNode = Node.allNode.size();
	    
		


	    
	    
	    
		/* ����ڵ��ϵ */
	    
	    LinkDataBase linkdatabase = new LinkDataBase();
	    int[][] nodeRalation = linkdatabase.inputFiberLink("��������.xls");  //����������·�������ڽӾ���ֵ
	    
	    


		
		
		
		/* ����ڵ����� */
		Node[] node = new Node[numOfAllNode];
		for(int i = 0;i<Node.allNode.size();i++) {
			node[i]=Node.allNode.get(i);       //������ת�����飬Ϊ�˷��Ϻ����㷨�ı�����ʽ
		}
		
		
	
		
		/* ������ڵ�������Ľڵ㼯�� */
		for(int i=0;i<nodeRalation.length;i++)
		{
			ArrayList<Node> List = new ArrayList<Node>();
			
			for(int j=0;j<nodeRalation[i].length;j++)
			{
				
				if(nodeRalation[i][j] != 0)
					List.add(node[j]);
			}
			node[i].setRelationNodes(List);
			List = null;  //�ͷ��ڴ�
		}

		/* ��ʼ��������·��  ���뻷112  
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
		ringNode[0] = node[2484];   //��������
		ringNode[1] = node [1210];  //���Ƽ�԰
		ringNode[2] = node[1609];  //�Ϻ��������й�ҵ԰
		ringNode[3] = node[132];   //��Ȩ���Ĵ���(��¥)
		ringNode[4] = node[467];   //��̩���͹�Ԣ(A¥1��Ԫ2F)
		ringNode[5] = node[2585];  //�������
		ringNode[6] = node[1647];  //������ƽ�������
		ringNode[7] = node[1283];  //�������
		ringNode[8] = node[418];   //����������ͷ
		*/
		
		/*
		int nodecount = 9;            //255
		Node[] ringNode = new Node[nodecount];
		ringNode[0] = node[489];   //����
		ringNode[1] = node [487];  //���ǹ��ʴ���
		ringNode[2] = node[1158];  //�Ƽ���ҵ����
		ringNode[3] = node[1497];   //�������
		ringNode[4] = node[526];   //���¿Ƽ���������
		ringNode[5] = node[832];  //�������ڴ���(10F)
		ringNode[6] = node[1741];  //Ʒ������
		ringNode[7] = node[2180];  //ͬ������
		ringNode[8] = node[1527];   //����
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

			System.out.println("��ǰ��Դ���޽⣬�������ڵ�����Ϣ����γ�ȣ��Ĺ����½��滮��");
			System.out.println("��ǰ����ΧΪ: "+layer);
			try{
				Thread.sleep(2000);
				}catch(Exception e){
				System.exit(0);//�˳�����
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
				List = null;  //�ͷ��ڴ�
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

	System.out.println("�����ʱ:"+(sysDateEnd-sysDateBegin)/60000+"��"+((sysDateEnd-sysDateBegin)/1000)%60+"��");


	}
}