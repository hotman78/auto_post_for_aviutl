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
import java.io.*;
import java.net.*;

public class setting extends JFrame implements ActionListener{
	private static final long serialVersionUID = -6692526963900250237L;
	JLabel label;
	static String movie_path;
	static String last_time_path;
	public static void main(String[] args) {
		movie_path="null";
		setting frame = new setting();
		frame.setBounds(10, 10, 300, 200);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setTitle("タイトル");
	    frame.setVisible(true);
		
	}
	setting(){
			String ver="0.1.0beta";
			//ファイル選択ボタン作成
		    JButton select = new JButton("送信ファイル選択");
		    select.addActionListener(this);
		    select.setActionCommand("select");
		    //決定ボタン作成
		    JButton send = new JButton("決定する");
		    send.addActionListener(this);
		    send.setActionCommand("send");
		    //初回のみコミュニティ登録
		    
		    //JCheckBox checkbox = new JCheckBox("hotmanのコミュニティに登録する", true);
		    //checkbox.setSelected(true);
		    //パネルに登録
		    JPanel buttonPanel = new JPanel();
		    JPanel buttonPanelUp = new JPanel();
		    //buttonPanelUp.add(checkbox);
		    buttonPanel.add(select);
		    buttonPanel.add(send);
		    label = new JLabel();

		    JPanel labelPanel = new JPanel();
		    labelPanel.add(label);
		    //labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));
		    getContentPane().add(labelPanel, BorderLayout.CENTER);
		    getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		    getContentPane().add(buttonPanelUp, BorderLayout.PAGE_START);
		    label.setText(getVer());
		    if (getVer().equals("error"))label.setText("最新バージョン取得に失敗しました");
		    else if(getVer().equals(ver))label.setText("最新バージョンです");
		    else label.setText("最新バージョンではありません更新して下さい");
	select.addActionListener( new ActionListener(){
        public void actionPerformed(ActionEvent event){
        	selectAction();
        }
	});
	send.addActionListener( new ActionListener(){
        public void actionPerformed(ActionEvent event){
        	sendAction();
        }
	});
/*	checkbox.addActionListener( new ActionListener(){
        public void actionPerformed(ActionEvent event){
        	checkboxAction(checkbox);
        }
	});*/
}
	public void checkboxAction(JCheckBox checkbox){}
	public void selectAction(){
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
	public void sendAction(){
		if(pathWhite(movie_path))System.exit(0);
		else label.setText("ファイル出力に失敗しました");
	}
	public void actionPerformed(ActionEvent e){}
	public static boolean set_data(){
		File file = new File("setting.txt");
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		BufferedWriter bw = new BufferedWriter(filewriter);
		PrintWriter pw = new PrintWriter(bw);
		pw.print("");
		pw.close();
		return true;
	}
	public static boolean pathWhite(String path){
		File file = new File("movie_path.txt");
		FileWriter filewriter;
		String old;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			old=br.readLine();
			br.close();
			filewriter = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		BufferedWriter bw = new BufferedWriter(filewriter);
		PrintWriter pw = new PrintWriter(bw);
		if(path.equals("null")){pw.print(old);pw.close();return false;}
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
    public static String getVer(){
    try {
            URL url = new URL("http://hotmanssite.web.fc2.com/auto_post_for_aviutl/version.txt");
            Object content = url.getContent();
            if (content instanceof InputStream) {
                BufferedReader bf = new BufferedReader(new InputStreamReader( (InputStream)content) );        
                String line;
                while ((line = bf.readLine()) != null) {
                    return line;
                }
            }
            else {
                return content.toString();
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e);
            return "error";
        }
        catch (IOException e) {
            System.err.println(e);
            return "error";
        }
	return "error";
    }
}