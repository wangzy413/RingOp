import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class LinkDataBase {

	public int[][] inputFiberLink(String str){
		FileInputStream fins=null;
		int[][] adj = new int[test.numOfAllNode][test.numOfAllNode];
		for(int i=0;i<adj.length;i++) {
			for(int j=0;j<adj.length;j++) {
				adj[i][j] = 0;
			}
		}
		int[][] adj1 = new int[test.numOfAllNode][test.numOfAllNode];
		for(int i=0;i<adj1.length;i++) {
			for(int j=0;j<adj1.length;j++) {
				adj1[i][j] = 0;
			}
		}
		test.buildRelation = adj1;
		

		
		try {
			fins = new FileInputStream("C:\\"+str);
		} catch (FileNotFoundException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		HSSFWorkbook wb = null;
		POIFSFileSystem fs = null;
		try {
			fs = new POIFSFileSystem(fins);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		HSSFSheet sheet=null;
		try {
			sheet =wb.getSheet("光缆");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int f=0;	
		
		if(sheet!=null) {

			outter:	for(Row row : sheet){
				

				if(row.getRowNum()!=0)
				{
					//格式控制，判断
	//				if(((row.getCell(0)==null)||(row.getCell(0)!=null&&row.getCell(0).getCellType()==Cell.CELL_TYPE_STRING&&row.getCell(0).getStringCellValue().trim().equals(""))||row.getCell(0).getCellType()==Cell.CELL_TYPE_BLANK)
	//						&&((row.getCell(2)==null)||(row.getCell(2)!=null&&row.getCell(2).getCellType()==Cell.CELL_TYPE_STRING&&row.getCell(2).getStringCellValue().trim().equals(""))||row.getCell(2).getCellType()==Cell.CELL_TYPE_BLANK)
	//						&&((row.getCell(3)==null)||(row.getCell(3)!=null&&row.getCell(3).getCellType()==Cell.CELL_TYPE_STRING&&row.getCell(3).getStringCellValue().trim().equals(""))||row.getCell(3).getCellType()==Cell.CELL_TYPE_BLANK)
	//				)
					if((row.getCell(2)==null) 
							|| (row.getCell(2)!=null&&row.getCell(2).getCellType()==Cell.CELL_TYPE_STRING&&row.getCell(2).getStringCellValue().trim().equals(""))
							|| (row.getCell(2).getCellType()==Cell.CELL_TYPE_BLANK)
							|| (row.getCell(3)==null) 
						|| (row.getCell(3)!=null&&row.getCell(3).getCellType()==Cell.CELL_TYPE_STRING&&row.getCell(3).getStringCellValue().trim().equals(""))
						|| (row.getCell(3).getCellType()==Cell.CELL_TYPE_BLANK)
						)
					{
						continue outter;
					}
					else
					{
						String A = "";
						String Z = "";
						
						Cell cell;
						try{
							cell=row.getCell(2);
							A = cell.getStringCellValue().trim();
						}catch(Exception e){
							e.printStackTrace();
						}
//						
						try{
							cell=row.getCell(3);
							Z = cell.getStringCellValue().trim();
						}catch(Exception e){
							e.printStackTrace();
						}
						
//				

						int aID = Integer.MAX_VALUE;
						int zID = Integer.MAX_VALUE;
						for(int i=0;i<Node.allNode.size();i++) {
							if(A.equals(Node.allNode.get(i).getName())){
								aID = Node.allNode.get(i).getId();
								break;

							}
						}
						for(int i=0;i<Node.allNode.size();i++) {
							if(Z.equals(Node.allNode.get(i).getName())) {
								zID = Node.allNode.get(i).getId();
								break;

							}
						}
						if (aID == Integer.MAX_VALUE || zID == Integer.MAX_VALUE)
							continue;
						if (f%5 == 0 && f!=0)
							System.out.print("!");
						if (f%500 == 0 && f!=0)
							System.out.println("");
						adj[aID][zID] = 1;
						adj[zID][aID] = 1;
						
					    f++;
						
					
					}
					
					
				   }

			     }

		  
		}
		System.out.println(" ");

		System.out.println("数据导入完成");
		return adj;

	}

}
