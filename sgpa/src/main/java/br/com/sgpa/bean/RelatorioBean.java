package br.com.sgpa.bean;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgpa.entity.Pagamento;
import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.entity.Processo;
import br.com.sgpa.enums.TipoProcesso;
import br.com.sgpa.utils.exception.ServiceException;

@ManagedBean(name = "relatorioBean")
@Component
@Scope(value = "session")
public class RelatorioBean extends Base {

	private BarChartModel model;
	private BarChartModel modelAdv;
	private BarChartModel modelProcessos;
	private PieChartModel pieModel;
	private List<Pagamento> listaPagamentosAdv;
	private List<Pagamento> listaPagamentosArea;
	private List<Processo> listaProcessos;
	private List<Processo> listaProcessosPie;

	// Filtros
	private Processo processoFiltro;
	private List<Pessoa> listaAdvogadoProcesso;
	@SuppressWarnings("unused")
	private List<TipoProcesso> tiposProcesso;

	public void init() {
		createAnimatedModels();
	}

	public String viewProcessos() {
		processoFiltro = new Processo();
		listaProcessos = new ArrayList<Processo>();
		carregaListasProcesso();
		return "/pages/relatorios/relatorioProcessos.xhtml?faces-redirect=true";
	}

	public String viewPorcentagem() {
		createPieModel();
		return "/pages/relatorios/relatorioPorcentagem.xhtml?faces-redirect=true";
	}

	public String consultarProcessos() {
		listaProcessos = new ArrayList<Processo>();
		try {
			if (processoFiltro.getDtFim() != null && processoFiltro.getDtInicio() == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha a data inicial", ""));
			}
			listaProcessos = processoBusiness.listaProcessoPorTipoPessoa(processoFiltro, true);
			criarModelProcessos(listaProcessos);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/pages/relatorios/relatorioProcessos.xhtml?faces-redirect=true";
	}

	private void carregaListasProcesso() {
		listaAdvogadoProcesso = new ArrayList<Pessoa>();
		try {
			listaAdvogadoProcesso.addAll(pessoaBusiness.listaPessoas("advogado"));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public String view() {
		init();
		return "/pages/relatorios/relatorios.xhtml?faces-redirect=true";
	}

	private void createAnimatedModels() {
		model = initBarModel();
		model.setTitle("Rentabilidade por área jurídica");
		model.setAnimate(true);
		model.setLegendPosition("n");
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setTickFormat("R$ %'.2f");
		yAxis.setMin(0);
		yAxis.setMax(180000);
		// Axis xAxis = model.getAxis(AxisType.X);
		// xAxis.setLabel("TIPOS DE PROCESSOS");

		modelAdv = initBarModelAdv();
		modelAdv.setTitle("Rentabilidade por advogado");
		modelAdv.setAnimate(true);
		modelAdv.setLegendPosition("n");
		Axis yAxis1 = modelAdv.getAxis(AxisType.Y);
		yAxis1.setTickFormat("R$ %'.2f");
		yAxis1.setMin(0);
		yAxis1.setMax(180000);
		// Axis xAxis1 = modelAdv.getAxis(AxisType.X);
		// xAxis1.setLabel("ADVOGADOS");

	}

	private void criarModelProcessos(List<Processo> listaProcessos) {
		modelProcessos = new BarChartModel();

		String advogado = null;
		ChartSeries ganho = new ChartSeries();
		ChartSeries perdido = new ChartSeries();
		BigDecimal somaProcPerdido = BigDecimal.ZERO;
		BigDecimal somaProcGanho = BigDecimal.ZERO;

		ganho.setLabel("Processos Ganhos");
		perdido.setLabel("Processos Perdidos");
		for (Processo processo : listaProcessos) {
			if (advogado == null) {
				advogado = processo.getAdvogado().getNome();
			}
			if (!advogado.equals(processo.getAdvogado().getNome())) {
				perdido.set(advogado, somaProcPerdido);
				ganho.set(advogado, somaProcGanho);
				somaProcPerdido = BigDecimal.ZERO;
				somaProcGanho = BigDecimal.ZERO;
				advogado = processo.getAdvogado().getNome();
				perdido.set(advogado, somaProcPerdido);
				ganho.set(advogado, somaProcGanho);
				if ("PERDIDO".equals(processo.getStProcesso())) {
					somaProcPerdido = somaProcPerdido.add(BigDecimal.ONE);
				} else {
					somaProcGanho = somaProcGanho.add(BigDecimal.ONE);
				}
			} else {
				if ("PERDIDO".equals(processo.getStProcesso())) {
					somaProcPerdido = somaProcPerdido.add(BigDecimal.ONE);
				} else {
					somaProcGanho = somaProcGanho.add(BigDecimal.ONE);
				}
			}
		}
		perdido.set(advogado, somaProcPerdido);
		ganho.set(advogado, somaProcGanho);
		modelProcessos.addSeries(ganho);
		modelProcessos.addSeries(perdido);

		modelProcessos.setLegendPosition("e");
		// modelProcessos.setStacked(true);

		Axis xModelProcessos = modelProcessos.getAxis(AxisType.X);
		xModelProcessos.setLabel("Advogados");
		xModelProcessos.setMin(0);
		xModelProcessos.setMax(200);

		Axis yModelProcessos = modelProcessos.getAxis(AxisType.Y);
		yModelProcessos.setLabel("Processos");
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();
		listaPagamentosArea = new ArrayList<Pagamento>();
		try {
			listaPagamentosArea = pagamentoBusiness.getListaPagamentos(false);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String tipoProcesso = null;
		BigDecimal soma = BigDecimal.ZERO;
		ChartSeries tpProcesso = new ChartSeries();
		List<ChartSeries> pagamentos = new ArrayList<ChartSeries>();
		for (Pagamento pagamento : listaPagamentosArea) {
			if (tipoProcesso == null) {
				tipoProcesso = pagamento.getProcesso().getTpProcesso().getDescricao();
				tpProcesso.setLabel(tipoProcesso);
			}
			if (!tipoProcesso.equals(pagamento.getProcesso().getTpProcesso().getDescricao())) {
				tpProcesso.set("TIPOS DE PROCESSO", soma);
				pagamentos.add(tpProcesso);
				tpProcesso = new ChartSeries();
				soma = BigDecimal.ZERO;
				tipoProcesso = pagamento.getProcesso().getTpProcesso().getDescricao();
				tpProcesso.setLabel(tipoProcesso);
				soma = soma.add(pagamento.getValorPagamento());
			} else {
				soma = soma.add(pagamento.getValorPagamento());
			}
		}
		tpProcesso.set("TIPOS DE PROCESSO", soma);
		pagamentos.add(tpProcesso);
		for (ChartSeries cs : pagamentos) {
			model.addSeries(cs);
		}
		return model;
	}

	private BarChartModel initBarModelAdv() {
		BarChartModel model = new BarChartModel();
		listaPagamentosAdv = new ArrayList<Pagamento>();
		try {
			listaPagamentosAdv = pagamentoBusiness.getListaPagamentos(true);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		String advogado = null;
		ChartSeries adv = new ChartSeries();
		BigDecimal somaAdv = BigDecimal.ZERO;
		for (Pagamento pagamento : listaPagamentosAdv) {
			if (advogado == null) {
				advogado = pagamento.getProcesso().getAdvogado().getNome();
				adv.setLabel(advogado);
			}
			if (!advogado.equals(pagamento.getProcesso().getAdvogado().getNome())) {
				adv.set("ADVOGADOS", somaAdv);
				model.addSeries(adv);
				advogado = pagamento.getProcesso().getAdvogado().getNome();
				adv = new ChartSeries();
				somaAdv = BigDecimal.ZERO;
				adv.setLabel(advogado);
				somaAdv = somaAdv.add(pagamento.getValorPagamento());
			} else {
				somaAdv = somaAdv.add(pagamento.getValorPagamento());
			}
		}
		adv.set("ADVOGADOS", somaAdv);
		model.addSeries(adv);
		return model;
	}

	private void createPieModel() {
		pieModel = new PieChartModel();

		// pieModel.set("Menos de 6 meses", 540);
		// pieModel.set("De 6 à 12 meses", 325);
		// pieModel.set("De 12 à 24 meses", 702);
		// pieModel.set("Acima de 24 meses", 421);

		BigDecimal qtdMenos6meses = BigDecimal.ZERO;
		BigDecimal qtdSeisA12meses = BigDecimal.ZERO;
		BigDecimal qtd12A24meses = BigDecimal.ZERO;
		BigDecimal qtdAcima24meses = BigDecimal.ZERO;
		listaProcessosPie = new ArrayList<Processo>();
		try {
			listaProcessosPie = processoBusiness.getListaProcessos();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		long diferenca = 0;
		for (Processo processo : listaProcessosPie) {
			try {
				if (processo.getDtInicio() != null && processo.getDtFim() != null)
					diferenca = calcular(processo.getDtInicio().toString(), processo.getDtFim().toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (diferenca > 0 && diferenca < 180) {
				qtdMenos6meses = qtdMenos6meses.add(BigDecimal.ONE);
			} else if (diferenca > 180 && diferenca < 365) {
				qtdSeisA12meses = qtdSeisA12meses.add(BigDecimal.ONE);
			} else if (diferenca > 365 && diferenca < 730) {
				qtd12A24meses = qtd12A24meses.add(BigDecimal.ONE);
			} else if (diferenca > 730) {
				qtdAcima24meses = qtdAcima24meses.add(BigDecimal.ONE);
			}
		}
		pieModel.set("Menos de 6 meses", qtdMenos6meses);
		pieModel.set("De 6 à 12 meses", qtdSeisA12meses);
		pieModel.set("De 12 à 24 meses", qtd12A24meses);
		pieModel.set("Acima de 24 meses", qtdAcima24meses);

		// pieModel.setTitle("Porcentagem por tempo de duração");
		pieModel.setLegendPosition("e");
		pieModel.setFill(true);
		pieModel.setShowDataLabels(true);
		pieModel.setDiameter(270);
	}

	public BarChartModel getModel() {
		return model;
	}

	public BarChartModel getModelAdv() {
		return modelAdv;
	}

	public BarChartModel getModelProcessos() {
		return modelProcessos;
	}

	public List<Processo> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(List<Processo> listaProcessos) {
		this.listaProcessos = listaProcessos;
	}

	public List<Pagamento> getListaPagamentosAdv() {
		return listaPagamentosAdv;
	}

	public void setListaPagamentosAdv(List<Pagamento> listaPagamentosAdv) {
		this.listaPagamentosAdv = listaPagamentosAdv;
	}

	public List<Pagamento> getListaPagamentosArea() {
		return listaPagamentosArea;
	}

	public void setListaPagamentosArea(List<Pagamento> listaPagamentosArea) {
		this.listaPagamentosArea = listaPagamentosArea;
	}

	public Processo getProcessoFiltro() {
		return processoFiltro;
	}

	public void setProcessoFiltro(Processo processoFiltro) {
		this.processoFiltro = processoFiltro;
	}

	public List<Pessoa> getListaAdvogadoProcesso() {
		return listaAdvogadoProcesso;
	}

	public void setListaAdvogadoProcesso(List<Pessoa> listaAdvogadoProcesso) {
		this.listaAdvogadoProcesso = listaAdvogadoProcesso;
	}

	public List<TipoProcesso> getTiposProcesso() {
		return TipoProcesso.getListaTipoProcesso();
	}

	public void setTiposProcesso(List<TipoProcesso> tiposProcesso) {
		this.tiposProcesso = tiposProcesso;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	/** Calcula o número de dias entre duas datas. */
	public static long calcular(String inicio, String fim) throws ParseException {
		Date dtInicial = df.parse(inicio);
		Date dtFinal = df.parse(fim);
		return (dtFinal.getTime() - dtInicial.getTime() + 3600000L) / 86400000L;
	}

	public List<Processo> getListaProcessosPie() {
		return listaProcessosPie;
	}

	public void setListaProcessosPie(List<Processo> listaProcessosPie) {
		this.listaProcessosPie = listaProcessosPie;
	}

}
