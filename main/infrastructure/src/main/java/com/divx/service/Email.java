package com.divx.service;


	import java.util.Properties;  
	  
	import javax.activation.DataHandler;  
	import javax.activation.FileDataSource;  
	import javax.mail.Address;  
	import javax.mail.BodyPart;  
	import javax.mail.Message;  
	import javax.mail.Multipart;  
	import javax.mail.Session;  
	import javax.mail.Transport;  
	import javax.mail.internet.InternetAddress;  
	import javax.mail.internet.MimeBodyPart;  
	import javax.mail.internet.MimeMessage;  
	import javax.mail.internet.MimeMultipart;  
	  
	  
	public class Email {   
	  
	    private MimeMessage mimeMsg;    
	    private Session session;    
	    private Properties props;    
	    private boolean needAuth = false;    
	      
	    private String connectusername;   
	    private String connectpassword;  
	    private Multipart mp; 
	    
	  public static class EmailModel {
		   private String smtp;
		   private String from;
		   private String to;
		   private String copyto;
		   private String subject;
		   private String content;
		   private String username;   
		   private String password; 
		   private String filename;
		   
		   public EmailModel(){
			   
		   }
		   
		   public String getSmtp() {
			return smtp;
		   }
		   public void setSmtp(String smtp) {
			this.smtp = smtp;
		   }
			public String getFrom() {
				return from;
			}
			public void setFrom(String from) {
				this.from = from;
			}
			public String getTo() {
				return to;
			}
			public void setTo(String to) {
				this.to = to;
			}
			public String getCopyto() {
				return copyto;
			}
			public void setCopyto(String copyto) {
				this.copyto = copyto;
			}
			public String getSubject() {
				return subject;
			}
			public void setSubject(String subject) {
				this.subject = subject;
			}
			public String getContent() {
				return content;
			}
			public void setContent(String content) {
				this.content = content;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
			public String getFilename() {
				return filename;
			}
			public void setFilename(String filename) {
				this.filename = filename;
			}
		   
	   }   
	      
	    public Email(String smtp){   
	        setSmtpHost(smtp);   
	        createMimeMessage();   
	    }   
	  
	    
	    public void setSmtpHost(String hostName) {   
	     
	        if(props == null)  
	            props = System.getProperties();     
	        props.put("mail.smtp.host",hostName);    
	    }   
	  
	  
	     
	    public boolean createMimeMessage()   
	    {   
	        try {   
	            
	            session = Session.getDefaultInstance(props,null);   
	        }   
	        catch(Exception e){   
	           
	            return false;   
	        }   
	      
	        
	        try {   
	            mimeMsg = new MimeMessage(session); 
	            mp = new MimeMultipart();   
	          
	            return true;   
	        } catch(Exception e){   
	            
	            return false;   
	        }   
	    }     
	      
	     
	    public void setNeedAuth(boolean need) {   
	        
	        if(props == null) props = System.getProperties();   
	        if(need){   
	            props.put("mail.smtp.auth","true");   
	        }else{   
	            props.put("mail.smtp.auth","false");   
	        }   
	    }   
	  
	    public void setNamePass(String name,String pass) {   
	        connectusername = name;   
	        connectpassword = pass;   
	    }   
	  
	    
	    public boolean setSubject(String mailSubject) {   
	        
	        try{   
	            mimeMsg.setSubject(mailSubject);   
	            return true;   
	        }   
	        catch(Exception e) {   
	          
	            return false;   
	        }   
	    }  
	      
	    
	    public boolean setBody(String mailBody) {   
	        try{   
	            BodyPart bp = new MimeBodyPart();   
	            bp.setContent(""+mailBody,"text/html;charset=GBK");   
	            mp.addBodyPart(bp);   
	          
	            return true;   
	        } catch(Exception e){   
	       
	        return false;   
	        }   
	    }   
	      
	    public boolean addFileAffix(String filename) {   
	      
	        
	        try{   
	            BodyPart bp = new MimeBodyPart();   
	            FileDataSource fileds = new FileDataSource(filename);   
	            bp.setDataHandler(new DataHandler(fileds));   
	            bp.setFileName(fileds.getName());   
	              
	            mp.addBodyPart(bp);   
	              
	            return true;   
	        } catch(Exception e){   
	            
	            return false;   
	        }   
	    }   
	      
	     
	    public boolean setFrom(String from) {   
	       
	        try{   
	            mimeMsg.setFrom(new InternetAddress(from));    
	            return true;   
	        } catch(Exception e) {   
	            return false;   
	        }   
	    }   
	      
	    public boolean setTo(String to){   
	        if(to == null)return false;   
	        try{   
	            mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));   
	            return true;   
	        } catch(Exception e) {   
	            return false;   
	        }     
	    }   
	      
	    
	    public boolean setCopyTo(String copyto)   
	    {   
	        if(copyto == null)return false;   
	        try{   
	        mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto));   
	        return true;   
	        }   
	        catch(Exception e)   
	        { return false; }   
	    }   
	      
	       
	    public boolean sendOut()   
	    {   
	        try{   
	            mimeMsg.setContent(mp);   
	            mimeMsg.saveChanges();   
	            
	              
	            Session mailSession = Session.getInstance(props,null);   
	            Transport transport = mailSession.getTransport("smtp");   
	            transport.connect((String)props.get("mail.smtp.host"),connectusername,connectpassword); 
	            transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));   
	           //transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC));   
	            //transport.send(mimeMsg);   
	              
	           
	            transport.close();   
	              
	            return true;   
	        } catch(Exception e) {   
	            
	            return false;   
	        }   
	    }   
	  
	    
	    public static boolean send(EmailModel emailModel) {  
	        Email theMail = new Email(emailModel.getSmtp());  
	        theMail.setNeedAuth(true);   
	          
	        if(!theMail.setSubject(emailModel.getSubject())) return false;  
	        if(!theMail.setBody(emailModel.getContent())) return false;  
	        if(!theMail.setTo(emailModel.getTo())) return false;  
	        if(!theMail.setFrom(emailModel.getFrom())) return false;  
	        theMail.setNamePass(emailModel.getUsername(),emailModel.getPassword());  
	          
	        if(!theMail.sendOut()) return false;  
	        return true;  
	    }  
	      
	   
	   public static boolean sendAndCc(EmailModel emailModel) {  
		   	Email theMail = new Email(emailModel.getSmtp());  
	        theMail.setNeedAuth(true);   
	          
	        if(!theMail.setSubject(emailModel.getSubject())) return false;  
	        if(!theMail.setBody(emailModel.getContent())) return false;  
	        if(!theMail.setTo(emailModel.getTo())) return false;  
	        if(!theMail.setCopyTo(emailModel.getCopyto())) return false;  
	        if(!theMail.setFrom(emailModel.getFrom())) return false;  
	        theMail.setNamePass(emailModel.getUsername(),emailModel.getPassword());  
	          
	        if(!theMail.sendOut()) return false;  
	        return true;  
	    }  
	      
	    
	    public static boolean sendAndFile(EmailModel emailModel) {  
	    	Email theMail = new Email(emailModel.getSmtp());  
	        theMail.setNeedAuth(true);   
	          
	        if(!theMail.setSubject(emailModel.getSubject())) return false;  
	        if(!theMail.setBody(emailModel.getContent())) return false;  
	        if(!theMail.addFileAffix(emailModel.getFilename())) return false;   
	        if(!theMail.setTo(emailModel.getTo())) return false; 
	        if(!theMail.setCopyTo(emailModel.getCopyto())) return false; 
	        if(!theMail.setFrom(emailModel.getFrom())) return false;  
	        theMail.setNamePass(emailModel.getUsername(),emailModel.getPassword());  
	          
	        if(!theMail.sendOut()) return false;  
	        return true;  
	    }  
	      
	    
	    public static boolean sendAndFileCc(EmailModel emailModel) {  
	    	Email theMail = new Email(emailModel.getSmtp());  
	        theMail.setNeedAuth(true);   
	          
	        if(!theMail.setSubject(emailModel.getSubject())) return false;  
	        if(!theMail.setBody(emailModel.getContent())) return false;  
	        if(!theMail.addFileAffix(emailModel.getFilename())) return false;   
	        if(!theMail.setTo(emailModel.getTo())) return false;  
	        if(!theMail.setFrom(emailModel.getFrom())) return false;  
	        theMail.setNamePass(emailModel.getUsername(),emailModel.getPassword()); 
	          
	        if(!theMail.sendOut()) return false;  
	        return true;  
	    }  
	      
	   
   
    
    
}
