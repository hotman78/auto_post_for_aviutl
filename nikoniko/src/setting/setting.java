package setting;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.BorderLayout;
import java.awt.event.*;

public class setting extends JFrame implements ActionListener{
	private static final long serialVersionUID = -6692526963900250237L;
	JLabel label;
	static String movie_path;
	static String last_time_path;
	public static void main(String[] args) {
		movie_path="null";
		setting frame = new setting();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBounds(10, 10, 300, 200);
	    frame.setTitle("タイトル");
	    frame.setVisible(true);
		
	}
	setting(){
			//ファイル選択ボタン作成
		    JButton select = new JButton("送信ファイル選択");
		    select.addActionListener(this);
		    select.setActionCommand("select");
		    //決定ボタン作成
		    JButton send = new JButton("決定する");
		    send.addActionListener(this);
		    send.setActionCommand("send");
		    //パネルに登録
		    JPanel buttonPanel = new JPanel();
		    buttonPanel.add(select);
		    buttonPanel.add(send);
		    label = new JLabel();

		    JPanel labelPanel = new JPanel();
		    labelPanel.add(label);

		    getContentPane().add(labelPanel, BorderLayout.CENTER);
		    getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
	  }
	public void actionPerformed(ActionEvent e){
		//パスをロード
		String cmd = e.getActionCommand();
		//ファイル選択
		if(cmd.equals("select")){
			String last_time_path=pathRead();	
			JFileChooser filechooser = new JFileChooser(last_time_path);
			int selected = filechooser.showSaveDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION){
				File file = filechooser.getSelectedFile();
				label.setText(file.getPath());
				movie_path=file.getPath();
			}else if (selected == JFileChooser.CANCEL_OPTION){
				label.setText("キャンセルされました");
			}else if (selected == JFileChooser.ERROR_OPTION){
				label.setText("エラー又は取消しがありました");
			}
		}
		if(cmd.equals("send")){
			if(pathWhite(movie_path))System.exit(0);
			else label.setText("ファイル出力に失敗しました");
		}
	}
	public static boolean pathWhite(String path){
		File file = new File("movie_path.txt");
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		BufferedWriter bw = new BufferedWriter(filewriter);
		PrintWriter pw = new PrintWriter(bw);
		if(path.equals("null")){pw.close();return false;}
		else pw.print(path);
		pw.close();
		return true;
	}
	public static String pathRead(){
	    try{
	    	File file = new File("movie_path.txt");
	    	BufferedReader br = new BufferedReader(new FileReader(file));

	    	String path = br.readLine();
	    	br.close();
	    	return(path);
	    }catch(FileNotFoundException e){
	    	System.out.println(e);
	    	return("error");
	    }catch(IOException e){
	    	System.out.println(e);
	    	return("error");
	    }
	}		
}
