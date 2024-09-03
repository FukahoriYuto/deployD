package jp.co.example.ec202110d.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * ユーザー登録情報のフォームクラス
 * @author iimura
 *
 */
public class CreateAccountForm {
	
	@NotBlank(message = "名前を入力してください")
	private String name;
	@NotBlank(message = "メールアドレスを入力してください！")
	@Email(message = "メールアドレスの形式が不正です")
	private String email;
	@NotBlank(message = "パスワードを入力して下さい")
	@Size(min = 8,max = 16,message = "パスワードは８文字以上１６文字以内で設定してください")
	private String password;
	@NotBlank(message = "確認用パスワードを入力して下さい")
	@Size(min = 8,max = 16,message = "パスワードは８文字以上１６文字以内で設定してください")
	private String confirmpassword;
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$",message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String zipcode;
	@NotBlank(message = "住所を入力してください")
	private String address;	
	@NotBlank(message = "電話番号を入力して下さい")
	@Pattern(regexp = "^[0-9]{2,5}-[0-9]{1,4}-[0-9]{4}$" ,message = "電話番号はXXXX-XXXX-XXXXの形式で入力してください")
	private String telephone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Override
	public String toString() {
		return "CreateAccountForm [name=" + name + ", email=" + email + ", password=" + password + ", confirmpassword="
				+ confirmpassword + ", zipcode=" + zipcode + ", address=" + address + ", telephone=" + telephone + "]";
	}
}
