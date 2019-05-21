package estagio.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Pereira
 */
public class CadastroController  implements Initializable {
//xml jobson ensinando a bugar

	@FXML
	private AnchorPane ap_menu;

	@FXML
	private JFXDrawer draw_menu;

	@FXML
	private VBox VB_Menu;

	@FXML
	private JFXButton Categoria;

	@FXML
	private JFXButton Cliente;

	@FXML
	private JFXButton Cidade;

	@FXML
	private JFXButton Estado;

	@FXML
	private JFXButton Empresa;

	@FXML
	private JFXButton Fornecedor;

	@FXML
	private JFXButton Produto;

	@FXML
	private JFXButton TipoVenda;

	@FXML
	private JFXButton Usuario;

	@FXML
	private JFXButton Sair;
	@FXML
	private JFXButton btn_texto;
	@FXML
	private StackPane stack_cadastros;

	@FXML
	private AnchorPane ap_cadastros;
	private Node node;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		btn_texto.setText("Olá,\n"+LoginController.logado.getNome());
		
		if (LoginController.logado.getTipo().equals("ADMIN") == true) {
			Categoria.setDisable(false);
			Empresa.setDisable(false);
			Fornecedor.setDisable(false);
			Produto.setDisable(false);
			TipoVenda.setDisable(false);
			Usuario.setDisable(false);
		}

	}

	@FXML
	private void OnActionCategoria(ActionEvent event) throws IOException {

		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/CategoriaFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

	@FXML
	private void OnActionCliente(ActionEvent event) throws IOException {

		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/ClienteFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

	@FXML
	private void OnActionCidade(ActionEvent event) throws IOException {

		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/CidadeFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

	@FXML
	private void OnActionEstado(ActionEvent event) throws IOException {

		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/EstadoFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

	@FXML
	private void OnActionEmpresa(ActionEvent event) throws IOException {
		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/EmpresaFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

	@FXML
	private void OnActionProduto(ActionEvent event) throws IOException {
		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/ProdutoFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

	@FXML
	private void OnActionUsuario(ActionEvent event) throws IOException {

		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/UsuarioFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

	@FXML
	private void OnActionFornecedor(ActionEvent event) throws IOException {

		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/FornecedorFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

	@FXML
	private void OnActionSair(ActionEvent event) throws IOException {

		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/MenuFXML.fxml"));
		ap_menu.getChildren().setAll(node);
	}

	@FXML
	void OnActionTipoVenda(ActionEvent event) throws IOException {

		node = (Node) FXMLLoader.load(getClass().getResource("/estagio/view/TipoVendaFXML.fxml"));
		ap_cadastros.getChildren().setAll(node);
	}

}
