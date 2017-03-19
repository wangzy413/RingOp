import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* ��ʾһ���ڵ��Լ�������ڵ����������нڵ� */
public class Node
{
	public String name = null;
	public ArrayList<Node> relationNodes = new ArrayList<Node>();
	private int id ;
    public  static List<Node>     allNode = new LinkedList<Node>();     //�洢���нڵ�
    private float longitude; //����
    private float latitude;  //γ��

	public Node (int id,String name, float longitude,float latitude){
		setName(name);
		setId(id);
		setLongitude(longitude);
		setLatitude(latitude);
	}
	
	public Node(){
		
	}

	public String getName() {
		return name;
	}
	
	   public static void	addCommonNode(Node e){//��̬��������ά������ע����ʵ�ʶ����޹�
	    	allNode.add(e);

		}	

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Node> getRelationNodes() {
		return relationNodes;
	}

	public void setRelationNodes(ArrayList<Node> relationNodes) {
		this.relationNodes = relationNodes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude2) {
		this.longitude = longitude2;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude2) {
		this.latitude = latitude2;
	}
}