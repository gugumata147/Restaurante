/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagio.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import estagio.dao.UsuarioDAO;
import estagio.model.Usuario;
import estagio.ui.notifications.FXNotification;
import estagio.view.util.TextFieldFormatterHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Pereira
 */
public class UsuarioController implements Initializable {

	@FXML
	private JFXTextField txt_codigo;
	@FXML
	private JFXButton btn_Buscar;
	@FXML
	private JFXTextField txt_nome;
	@FXML
	private JFXTextField txt_login;
	@FXML
	private JFXPasswordField txt_senha;
	@FXML
	private JFXComboBox<String> cbb_ativo;
	@FXML
	private JFXButton btn_Novo;
	@FXML
	private JFXButton btn_Gravar;
	@FXML
	private JFXButton btn_Excluir;
	@FXML
	private JFXButton btn_Cancelar;
	@FXML
	private JFXButton btn_Sair;
	@FXML
	private JFXComboBox<String> cbb_tipo;
	@FXML
	private Label lbl_codigo;
	@FXML
	private ContextMenu ctm_nome;
	@FXML
	private MenuItem mi_nome;
	@FXML
	private Label lbl_nome;
	@FXML
	private Tooltip ttp_lblNome;
	@FXML
	private ContextMenu ctm_login;
	@FXML
	private MenuItem mi_login;
	@FXML
	private Label lbl_login;
	@FXML
	private Tooltip ttp_lblLogin;
	@FXML
	private ContextMenu ctm_senha;
	@FXML
	private MenuItem mi_senha;
	@FXML
	private Label lbl_senha;
	@FXML
	private Tooltip ttp_lblSenha;
	@FXML
	private ContextMenu ctm_ativo;
	@FXML
	private MenuItem mi_ativo;
	@FXML
	private Label lbl_ativo;
	@FXML
	private Tooltip ttp_lblAtivo;
	@FXML
	private ContextMenu ctm_tipo;
	@FXML
	private MenuItem mi_tipo;
	@FXML
	private Label lbl_tipo;
	@FXML
	private Tooltip ttp_lblTipo;
	@FXML
	private Tooltip ttp_btnBuscar;
	@FXML
	private Tooltip ttp_btnNovo;
	@FXML
	private Tooltip ttp_btnGravar;
	@FXML
	private Tooltip ttp_btnExcluir;
	@FXML
	private Tooltip ttp_btnCancelar;
	@FXML
	private Tooltip ttp_btnSair;
	@FXML
	private AnchorPane ap_busca;
	@FXML
	private JFXButton btn_filtro;
	@FXML
	private JFXTextField txt_filtro;
	@FXML
	private TableView<Usuario> tb_usuario;
	@FXML
	private TableColumn<Usuario, String> tc_codigo;
	@FXML
	private TableColumn<Usuario, String> tc_nome;
	@FXML
	private TableColumn<Usuario, String> tc_login;
	@FXML
	private TableColumn<Usuario, String> tc_tipo;
	@FXML
	private TableColumn<Usuario, String> tc_ativo;
	@FXML
	private JFXButton btn_voltar;
	JFXAutoCompletePopup<String> autoCompletePopupTip = new JFXAutoCompletePopup<String>();
	JFXAutoCompletePopup<String> autoCompletePopupAti = new JFXAutoCompletePopup<String>();
	private ObservableList<String> cbb_Tipos;
	private ObservableList<String> cbb_Ativo;
	private UsuarioDAO usuarioDAO;
	private Usuario usuario;
	private String corErro = "-fx-border-color: red;";
	private String corNormal = "-fx-border-color:white";
	private ObservableList<Usuario> obslUsuario;
	private List<Usuario> listaUsuario;
	private TextFieldFormatterHelper tffh;
	private Long qt;
	private String antTipo;
	private String antAtivado;
	@FXML
	private AnchorPane ap_usuario;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@SuppressWarnings("static-access")
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		qt = null;
		usuarioDAO = new UsuarioDAO();
		usuario = new Usuario();
		cbb_Tipos = FXCollections.observableArrayList("ADMIN", "NORMAL");
		cbb_Ativo = FXCollections.observableArrayList("ATIVADO", "DESATIVADO");
		cbb_tipo.setItems(cbb_Tipos);
		cbb_ativo.setItems(cbb_Ativo);
		txt_filtro.setTextFormatter(tffh.getTextFieldToUpperFormatter("[a-zA-Z 0-9\\u00C0-\\u00FF]+", 100));
		txt_login.setTextFormatter(tffh.getTextFieldToUpperFormatter("[a-zA-Z 0-9\\u00C0-\\u00FF]+", 25));
		txt_nome.setTextFormatter(tffh.getTextFieldToUpperFormatter("[a-zA-Z 0-9\\u00C0-\\u00FF]+", 100));
		txt_senha.setTextFormatter(tffh.getTextFieldToUpperFormatter("[a-zA-Z 0-9\\u00C0-\\u00FF]+", 25));
		listaUsuario = new ArrayList<>();
		InitComboBoxAtiv();
		InitComboBoxTip();
		desativaTela();
		ap_usuario.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ESCAPE)) {
				if (ap_busca.isVisible() == true) {
					limpaBuscas();
					txt_nome.setFocusTraversable(true);
				} else {
					ap_usuario.setVisible(false);
				}

			}
			if (event.getCode().equals(KeyCode.F1) && ap_busca.isVisible() == false) {
				desativaTela();
			}
			if (event.getCode().equals(KeyCode.F2) && ap_busca.isVisible() == false) {
				gravar();
			}
			if (event.getCode().equals(KeyCode.F3) && ap_busca.isVisible() == false
					&& btn_Excluir.isDisable() == false) {
				excluir();
			}
			if (event.getCode().equals(KeyCode.F4) && ap_busca.isVisible() == false
					&& btn_Cancelar.isDisable() == false) {
				desativaTela();
			}

		});
	}

	public void carregaTela(String palavra) {
		listaUsuario.clear();
		tb_usuario.getItems().clear();
		tc_codigo.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tc_login.setCellValueFactory(new PropertyValueFactory<>("Login"));
		tc_tipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
		tc_ativo.setCellValueFactory(new PropertyValueFactory<>("Ativo"));
		listaUsuario = usuarioDAO.listar(palavra.toUpperCase());
		if (!listaUsuario.isEmpty()) {
			obslUsuario = FXCollections.observableArrayList(listaUsuario);
			tb_usuario.setItems(obslUsuario);
		}
	}

	@FXML
	private void OnActionBuscar(ActionEvent event) {

		ap_busca.setVisible(true);
		carregaTela(txt_filtro.getText());
	}

	@FXML
	private void OnActionNovo(ActionEvent event) {
		desativaTela();
		resetaDAO();
	}

	public void desativaTela() {
		txt_nome.setText("");
		txt_senha.setText("");
		txt_login.setText("");
		txt_codigo.setText("0");
		txt_nome.setStyle(corNormal);
		txt_senha.setStyle(corNormal);
		txt_login.setStyle(corNormal);
		cbb_ativo.setStyle(corNormal);
		cbb_tipo.setStyle(corNormal);
		btn_Excluir.setDisable(true);
		btn_Cancelar.setDisable(true);
		cbb_ativo.getSelectionModel().select(null);
		cbb_tipo.getSelectionModel().select(null);
		usuario = new Usuario();
	}

	public void gravar() {
		resetaDAO();
		usuario = new Usuario();
		boolean erro = false;
		if (txt_codigo.getText().equals("") == true) {
			erro = true;
		} else {
			usuario.setId(Long.parseLong(txt_codigo.getText()));
		}

		usuario.setNome(txt_nome.getText().replace(" ", ""));
		if (usuario.getNome().length() < 5) {
			erro = true;
			txt_nome.setStyle(corErro);
			ctm_nome.show(txt_nome, Side.RIGHT, 10, 0);
		} else {
			usuario.setNome(txt_nome.getText());
			txt_nome.setStyle(corNormal);
			ctm_nome.hide();
		}

		usuario.setSenha(txt_senha.getText().replace(" ", ""));
		if (usuario.getSenha().length() < 5) {
			erro = true;
			txt_senha.setStyle(corErro);
			ctm_senha.show(txt_senha, Side.RIGHT, 10, 0);
		} else {
			txt_senha.setStyle(corNormal);
			usuario.setSenha(txt_senha.getText());
			ctm_senha.hide();
		}

		if (cbb_ativo.getSelectionModel().getSelectedItem() == null) {
			erro = true;
			ctm_ativo.show(cbb_ativo, Side.RIGHT, 10, 0);
			cbb_ativo.setStyle(corErro);
		} else {
			usuario.setAtivo(cbb_ativo.getSelectionModel().getSelectedItem());
			cbb_ativo.setStyle(corNormal);
			ctm_ativo.hide();
		}

		if (cbb_tipo.getSelectionModel().getSelectedItem() == null) {
			erro = true;
			ctm_tipo.show(cbb_tipo, Side.RIGHT, 10, 0);
			cbb_tipo.setStyle(corErro);
		} else {
			usuario.setTipo(cbb_tipo.getSelectionModel().getSelectedItem());
			cbb_tipo.setStyle(corNormal);
			ctm_tipo.hide();
		}

		Usuario verificador = usuarioDAO.login_dup(txt_login.getText().toUpperCase());
		if (verificador != null) {
			if (verificador.getId().equals(usuario.getId()) == true) {
				erro = false;
				resetaDAO();
				txt_login.setStyle(corNormal);
				ctm_login.hide();
				usuario.setLogin(txt_login.getText());
			} else {
				erro = true;
				txt_login.setStyle(corErro);
				ctm_login.show(txt_login, Side.RIGHT, 10, 0);
			}
		} else {
			usuario.setLogin(txt_login.getText().replace(" ", ""));
			if (usuario.getLogin().length() < 5) {
				erro = true;
				txt_login.setStyle(corErro);
				ctm_login.show(txt_login, Side.RIGHT, 10, 0);
			} else {
				usuario.setLogin(txt_login.getText());
				resetaDAO();
				txt_login.setStyle(corNormal);
				ctm_login.hide();
			}
		}
		if (erro == false) {
			// Mesmo que o usuário consiga colocar um caracter minusculo, há um tratamento.
			usuario.setAtivo(usuario.getAtivo().toUpperCase());
			usuario.setLogin(usuario.getLogin().toUpperCase());
			usuario.setNome(usuario.getNome().toUpperCase());
			usuario.setSenha(usuario.getSenha().toUpperCase());
			usuario.setTipo(usuario.getTipo().toUpperCase());
			FXNotification fxn;
			if (usuario.getId() == 0) {
				usuario.setId(null);

				usuarioDAO.inserir(usuario);
				fxn = new FXNotification("Usuário: " + usuario.getNome() + " foi inserida.",
						FXNotification.NotificationType.INFORMATION);
				fxn.show();
				desativaTela();

			} else {
				qt = usuarioDAO.count_tipo("ADMIN");
				resetaDAO();
				if (antTipo.equals(usuario.getTipo()) == false && antTipo.equals("ADMIN")
						|| antAtivado.equals(usuario.getAtivo()) == false && antAtivado.equals("ATIVADO")
								&& antTipo.equals("ADMIN")) {
					if (qt < 2) {
						erro = true;
					}
				}
				if (erro == false) {
					usuarioDAO.alterar(usuario);
					fxn = new FXNotification("Usuário: " + usuario.getNome() + " foi alterado.",
							FXNotification.NotificationType.INFORMATION);
					fxn.show();
					desativaTela();
				} else {
					fxn = new FXNotification("Não foi possível alterar o usuário, pois há somente um ADMIN.",
							FXNotification.NotificationType.ERROR);
					fxn.show();
				}
			}

		} else {
			FXNotification fxn;
			fxn = new FXNotification("Corrija os erros destacados em vermelho.", FXNotification.NotificationType.ERROR);
			fxn.show();
		}
	}

	@FXML
	private void OnActionGravar(ActionEvent event) {
		gravar();
	}

	public void excluir() {

		Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
		ButtonType btnSim = new ButtonType("Sim");
		ButtonType btnNao = new ButtonType("Não");
		dialogoExe.setTitle("");
		dialogoExe.setHeaderText("Você deseja realmente excluir " + usuario.getNome() + "?");
		dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
		dialogoExe.showAndWait().ifPresent(b -> {
			if (b == btnSim) {
				boolean Error = false;
				FXNotification fxn;
				if (txt_codigo.getText().equals("") != true && !txt_codigo.getText().isEmpty()) {
					qt = usuarioDAO.count_tipo("ADMIN");
					resetaDAO();
					if (qt < 2) {
						if (usuario.getTipo().equals("ADMIN") == true) {
							Error = true;
							fxn = new FXNotification("Usuário: " + usuario.getNome() + " não pode ser excluido.",
									FXNotification.NotificationType.WARNING);
							fxn.show();
						}
					}
					if (Error == false) {

						usuario.setId(Long.parseLong(txt_codigo.getText()));
						usuarioDAO.Deletar(usuario);
						fxn = new FXNotification("Usuário: " + usuario.getNome() + " foi excluido.",
								FXNotification.NotificationType.INFORMATION);
						fxn.show();
					}

				}
				desativaTela();
			}
		});
	}

	@FXML
	private void OnActionExcluir(ActionEvent event) {
		excluir();
	}

	@FXML
	private void OnActionCancelar(ActionEvent event) {
		desativaTela();
		resetaDAO();

	}

	@FXML
	private void OnActionSair(ActionEvent event) {
		ap_usuario.setVisible(false);

	}

	@FXML
	private void Limitetxt_Nome(KeyEvent event) {
		if (txt_nome.getText().length() == 100) {
			event.consume();
		}
	}

	@FXML
	private void OnActionFiltro(ActionEvent event) {
		if (txt_filtro.getText() != null) {
			carregaTela(txt_filtro.getText());
		}
	}

	@FXML
	private void OnKeyPressedEnter(KeyEvent event) {
		txt_filtro.setOnKeyPressed((KeyEvent event1) -> {
			if (event1.getCode() == KeyCode.ENTER) {
				carregaTela(txt_filtro.getText());
			}
		});
	}

	@FXML
	private void Limitetxt_filtro(KeyEvent event) {
		if (txt_filtro.getText().length() == 100) {
			event.consume();
		}
	}

	@FXML
	private void OnMouseClickedUsuario(MouseEvent event) {

		if (tb_usuario.getSelectionModel().getSelectedItem() != null) {
			this.setUsuario(tb_usuario.getSelectionModel().getSelectedItem());
		}

	}

	public void setUsuario(Usuario usuario) {

		resetaDAO();
		this.usuario = usuario;
		txt_nome.setText(usuario.getNome());
		txt_senha.setText(usuario.getSenha());
		txt_login.setText(usuario.getLogin());
		txt_codigo.setText("" + usuario.getId());
		cbb_ativo.getSelectionModel().select(usuario.getAtivo());
		cbb_tipo.getSelectionModel().select(usuario.getTipo());
		antTipo = usuario.getTipo();
		antAtivado = usuario.getAtivo();
		limpaBuscas();
		ativaTela();
	}

	public void resetaDAO() {
		usuarioDAO = new UsuarioDAO();
	}

	@FXML
	private void OnActionVoltar(ActionEvent event) {
		ap_busca.setVisible(false);
		limpaBuscas();
	}

	public void limpaBuscas() {
		listaUsuario.clear();
		tb_usuario.getItems().clear();
		ap_busca.setVisible(false);
		txt_filtro.setText("");
	}

	public void ativaTela() {
		btn_Excluir.setDisable(false);
		btn_Cancelar.setDisable(false);
	}

	private void InitComboBoxTip() {
		autoCompletePopupTip.getSuggestions().clear();
		autoCompletePopupTip.getSuggestions().addAll(cbb_tipo.getItems());

		autoCompletePopupTip.setSelectionHandler(eventt -> {
			cbb_tipo.setValue(eventt.getObject());
			cbb_tipo.getSelectionModel().select(eventt.getObject());
		});
		autoCompletePopupTip.setStyle(
				"-fx-control-inner-background:WHITE;" + "-fx-accent: #00A279;" + "" + "-fx-font:14px 'Arial'");
		TextField editor = cbb_tipo.getEditor();
		editor.textProperty().addListener(observable -> {
			// The filter method uses the Predicate to /filter the Suggestions defined above
			// I choose to use the contains method while ignoring cases
			autoCompletePopupTip.filter(item -> item.contains(editor.getText().toUpperCase()));
			autoCompletePopupTip.setHideOnEscape(false);
			autoCompletePopupTip.setAutoFix(false);
			// Hide the autocomplete popup if the filtered suggestions is empty or when the
			// box's original popup is open
			if (autoCompletePopupTip.getFilteredSuggestions().isEmpty() || cbb_tipo.showingProperty().get()
					|| cbb_tipo.getEditor().isFocused() == false) {
				autoCompletePopupTip.hide();
			} else {
				autoCompletePopupTip.show(editor);
			}
		});
		cbb_tipo.setConverter(new StringConverter<String>() {

			@Override
			public String toString(String provinceState) {
				if (provinceState == null)
					return "";
				return provinceState.toString();
			}

			@Override
			public String fromString(String string) {
				try {
					int index = cbb_tipo.getSelectionModel().getSelectedIndex();
					return cbb_tipo.getItems().get(index);
				} catch (Exception e) {
					return null;
				}

			}
		});
	}

	private void InitComboBoxAtiv() {
		autoCompletePopupAti.getSuggestions().addAll(cbb_ativo.getItems());

		autoCompletePopupAti.setSelectionHandler(eventt -> {
			cbb_ativo.setValue(eventt.getObject());
			cbb_ativo.getSelectionModel().select(eventt.getObject());
		});
		autoCompletePopupAti.setStyle(
				"-fx-control-inner-background:WHITE;" + "-fx-accent: #00A279;" + "" + "-fx-font:14px 'Arial'");
		TextField editor = cbb_ativo.getEditor();
		editor.textProperty().addListener(observable -> {
			// The filter method uses the Predicate to /filter the Suggestions defined above
			// I choose to use the contains method while ignoring cases
			autoCompletePopupAti.filter(item -> item.contains(editor.getText().toUpperCase()));
			autoCompletePopupAti.setHideOnEscape(false);
			autoCompletePopupAti.setAutoFix(false);
			// Hide the autocomplete popup if the filtered suggestions is empty or when the
			// box's original popup is open
			if (autoCompletePopupAti.getFilteredSuggestions().isEmpty() || cbb_ativo.showingProperty().get()
					|| cbb_ativo.getEditor().isFocused() == false) {
				autoCompletePopupAti.hide();
			} else {
				autoCompletePopupAti.show(editor);
			}
		});
		cbb_ativo.setConverter(new StringConverter<String>() {

			@Override
			public String toString(String provinceState) {
				if (provinceState == null)
					return "";
				return provinceState.toString();
			}

			@Override
			public String fromString(String string) {
				try {
					int index = cbb_ativo.getSelectionModel().getSelectedIndex();
					return cbb_ativo.getItems().get(index);
				} catch (Exception e) {
					return null;
				}

			}
		});
	}

	@FXML
	private void Limitetxt_Login(KeyEvent event) {

	}

	@FXML
	private void Limitetxt_Senha(KeyEvent event) {

	}

}
