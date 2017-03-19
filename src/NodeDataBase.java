import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;



public class NodeDataBase {
	
	public void inputNode(String str){    //str��xsl�������
		FileInputStream fins=null;
		
		try {
			fins = new FileInputStream("C:\\"+str);
		} catch (FileNotFoundException e1) {
			// TODO �Զ����� catch ��
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
			sheet =wb.getSheet("����");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int f=0;	
		if(sheet!=null) {

			outter:	for(Row row : sheet){
				

				if(row.getRowNum()!=0)
				{
					//��ʽ���ƣ��ж�
					if(((row.getCell(0)==null)||(row.getCell(0)!=null&&row.getCell(0).getCellType()==Cell.CELL_TYPE_STRING&&row.getCell(0).getStringCellValue().trim().equals(""))||row.getCell(0).getCellType()==Cell.CELL_TYPE_BLANK)
							&&((row.getCell(1)==null)||(row.getCell(1)!=null&&row.getCell(1).getCellType()==Cell.CELL_TYPE_STRING&&row.getCell(1).getStringCellValue().trim().equals(""))||row.getCell(1).getCellType()==Cell.CELL_TYPE_BLANK)

					)
					{
						break outter;
					}
					else
					{
						int id=Integer.MAX_VALUE;
						String name="";
						float longitude = -1;
						float latitude = -1;
						
						Cell cell;
						try{
							cell=row.getCell(0);
							id = (int) cell.getNumericCellValue();
						}catch(Exception e){
							e.printStackTrace();
						}
//						
						try{
							cell=row.getCell(1);
							name=cell.getStringCellValue().trim();
						}catch(Exception e){
							e.printStackTrace();
						}
						
						try{
							cell=row.getCell(2);
							longitude = (float) cell.getNumericCellValue();
						}catch(Exception e){
							e.printStackTrace();
						}
						try{
							cell=row.getCell(3);
							latitude = (float) cell.getNumericCellValue();
						}catch(Exception e){
							e.printStackTrace();
						}
						
//				
						

						Node node = new Node(id,name,longitude,latitude);
							
						Node.addCommonNode(node);
							
					    f++;
						
					
					}
					
				   }

			     }

		  
		}

		
	}

}
