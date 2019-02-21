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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import estagio.dao.EstadoDAO;
import estagio.model.Estado;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Pereira
 */
public class EstadoController implements Initializable {

	@FXML
	private AnchorPane ap_busca;
	@FXML
	private AnchorPane ap_estado;
	@FXML
	private JFXButton btn_Buscar;
	@FXML
	private JFXButton btn_Cancelar;
	@FXML
	private JFXButton btn_Excluir;
	@FXML
	private JFXButton btn_filtro;
	@FXML
	private JFXButton btn_Gravar;
	@FXML
	private JFXButton btn_Novo;
	@FXML
	private JFXButton btn_Sair;
	@FXML
	private JFXButton btn_voltar;
	private String corErro = "-fx-border-color: red;";
	private String corNormal = "-fx-border-color:white";
	@FXML
	private ContextMenu ctm_nome;
	@FXML
	private ContextMenu ctm_sigla;
	private Estado estado;
	private EstadoDAO estadoDAO;
	private List<Estado> listaEstado;
	@FXML
	private MenuItem mi_nome;
	@FXML
	private MenuItem mi_sigla;
	private ObservableList<Estado> obslEstado;
	@FXML
	private TableView<Estado> tb_estado;
	@FXML
	private TableColumn<Estado, String> tc_codigo;
	@FXML
	private TableColumn<Estado, String> tc_nome;
	@FXML
	private TableColumn<Estado, String> tc_sigla;
	private TextFieldFormatterHelper tffh;
	@FXML
	private Tooltip ttp_btnBuscar;
	@FXML
	private Tooltip ttp_btnCancelar;
	@FXML
	private Tooltip ttp_btnExcluir;
	@FXML
	private Tooltip ttp_btnGravar;
	@FXML
	private Tooltip ttp_btnNovo;
	@FXML
	private Tooltip ttp_btnSair;
	@FXML
	private Tooltip ttp_codigo;
	@FXML
	private Tooltip ttp_nome;
	@FXML
	private Tooltip ttp_Nome;
	@FXML
	private Tooltip ttp_sigla;
	@FXML
	private Tooltip ttp_Sigla;
	@FXML
	private JFXTextField txt_codigo;
	@FXML
	private JFXTextField txt_filtro;
	@FXML
	private JFXTextField txt_nome;
	@FXML
	private JFXTextField txt_sigla;

	public void ativaTela() {
		btn_Excluir.setDisable(false);
		btn_Cancelar.setDisable(false);
	}

	public void carregaTela(String palavra) {
		listaEstado.clear();
		tb_estado.getItems().clear();
		tc_codigo.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tc_sigla.setCellValueFactory(new PropertyValueFactory<>("Uf"));
		listaEstado = estadoDAO.listar(palavra.toUpperCase());
		if (!listaEstado.isEmpty()) {
			obslEstado = FXCollections.observableArrayList(listaEstado);
			tb_estado.setItems(obslEstado);
		}

	}

	public void desativaTela() {
		txt_nome.setText("");
		txt_sigla.setText("");
		txt_codigo.setText("0");
		btn_Excluir.setDisable(true);
		btn_Cancelar.setDisable(true);
		txt_nome.setStyle(corNormal);
		txt_sigla.setStyle(corNormal);
		ctm_nome.hide();
		ctm_sigla.hide();
	}

	public Estado getEstado() {
		return estado;
	}

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@SuppressWarnings("static-access")
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		estado = new Estado();
		resetaDAO();
		desativaTela();
		txt_nome.setTextFormatter(tffh.getUpperCaseTextFieldFormatter());
		txt_sigla.setTextFormatter(tffh.getUpperCaseTextFieldFormatter());
		txt_filtro.setTextFormatter(tffh.getUpperCaseTextFieldFormatter());
		listaEstado = new ArrayList<>();
	}

	@FXML
	private void Limitetxt_filtro(KeyEvent event) {
		if (txt_filtro.getText().length() == 100)
			event.consume();
	}

	@FXML
	private void Limitetxt_Nome(KeyEvent event) {
		if (txt_nome.getText().length() == 100)
			event.consume();

		txt_nome.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\sa-zA-Z-0-9*")) {
				txt_nome.setText(newValue.replaceAll("[^\\sa-zA-Z-0-9]", ""));
			}
		});
	}

	@FXML
	private void Limitetxt_Sigla(KeyEvent event) {
		if (txt_sigla.getText().length() == 2)
			event.consume();
		txt_sigla.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("a-zA-Z*")) {
				txt_sigla.setText(newValue.replaceAll("[^a-zA-Z]", ""));
			}
		});
	}

	public void limpaBuscas() {
		listaEstado.clear();
		tb_estado.getItems().clear();
		ap_busca.setVisible(false);
		txt_filtro.setText("");
	}

	@FXML
	private void OnActionBuscar(ActionEvent event) {
		ap_busca.setVisible(true);
		carregaTela(txt_filtro.getText());

	}

	@FXML
	private void OnActionCancelar(ActionEvent event) {
		estado = new Estado();
		estadoDAO = new EstadoDAO();
		desativaTela();
	}

	@FXML
	private void OnActionExcluir(ActionEvent event) {
		resetaDAO();
		Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
		ButtonType btnSim = new ButtonType("Sim");
		ButtonType btnNao = new ButtonType("Não");
		dialogoExe.setTitle("");
		dialogoExe.setHeaderText("Você deseja realmente excluir " + estado.getNome() + "?");
		dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
		dialogoExe.showAndWait().ifPresent(b -> {
			if (b == btnSim) {
				FXNotification fxn;

				if (txt_codigo.getText().equals("") != true) {
					Boolean deletado = estadoDAO.Deletar(estado);
					if (deletado == true) {
						fxn = new FXNotification("Estado: " + estado.getNome() + " foi excluido.",
								FXNotification.NotificationType.INFORMATION);

						desativaTela();
					} else {
						fxn = new FXNotification("Estado: " + estado.getNome() + " não pode ser excluido.",
								FXNotification.NotificationType.INFORMATION);
					}
					fxn.show();
				}
			}
		});
	}

	@FXML
	private void OnActionFiltro(ActionEvent event) {
		carregaTela(txt_filtro.getText());
	}

	@FXML
	private void OnActionGravar(ActionEvent event) {
		Boolean erro = false;
		estado = new Estado();
		resetaDAO();
		String nome = txt_nome.getText();
		nome = nome.replaceAll("\\s", "");
		if (txt_nome.getText().equals("") == true || nome.equals("") == true || nome.length() < 3) {
			txt_nome.setStyle(corErro);
			ctm_nome.show(txt_nome, Side.RIGHT, 10, 0);
			erro = true;
		} else {
			txt_nome.setStyle(corNormal);
			estado.setNome(txt_nome.getText());
			ctm_nome.hide();
		}
		if (txt_sigla.getText().equals("") == true || txt_sigla.getText().length() != 2) {
			erro = true;
			ctm_sigla.show(txt_sigla, Side.RIGHT, 10, 0);
			txt_sigla.setStyle(corErro);
		} else {
			estado.setUf(txt_sigla.getText());
			txt_sigla.setStyle(corNormal);
			ctm_sigla.hide();
		}
		if (txt_codigo.getText().equals("")) {
			erro = true;
		} else {
			estado.setId(Long.parseLong(txt_codigo.getText()));
		}
		if (erro != true) {
			Alert dialogoExe = new Alert(Alert.AlertType.INFORMATION);
			dialogoExe.setTitle("Gravar");
			FXNotification fxn;
			if (estado.getId() == 0) {
				estado.setId(null);
				estadoDAO.inserir(estado);
				fxn = new FXNotification("Estado: " + estado.getNome() + " inserida.",
						FXNotification.NotificationType.INFORMATION);
			} else {
				estadoDAO.alterar(estado);
				fxn = new FXNotification("Estado: " + estado.getNome() + " alterada.",
						FXNotification.NotificationType.INFORMATION);
			}
			fxn.show();
			desativaTela();
			resetaDAO();

		}
	}

	@FXML
	private void OnActionNovo(ActionEvent event) {
		estado = new Estado();
		resetaDAO();
		desativaTela();
	}

	@FXML
	private void OnActionSair(ActionEvent event) {
		ap_estado.setVisible(false);
	}

	@FXML
	private void OnActionVoltar(ActionEvent event) {
		limpaBuscas();
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
	private void OnMouseClickedEstado(MouseEvent event) {
		if (tb_estado.getSelectionModel().getSelectedItem() != null) {
			this.setEstado(tb_estado.getSelectionModel().getSelectedItem());
			limpaBuscas();
		}
	}

	public void resetaDAO() {
		estadoDAO = new EstadoDAO();
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
		txt_nome.setText(estado.getNome());
		txt_codigo.setText("" + estado.getId());
		txt_sigla.setText(estado.getUf());
		ativaTela();
	}

}
