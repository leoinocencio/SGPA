package br.com.sgpa.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class Utils {
	
	//Relatorios
	public static final String DIRETORIO_RELATORIOS = "WEB-INF/relatorios/";
	public static final String CAMINHO_LOGO = "images/marcacnj.jpg";
	public static final String REGIAO_NORDESTE = "'AL','BA','CE','MA','PB','PE','PI','RN','SE'";
	public static final String REGIAO_NORTE = "'AC','AP','AM','PA','RO','RR','TO'";
	public static final String REGIAO_SUL = "'PR','SC','RS'";
	public static final String REGIAO_SUDESTE = "'ES','MG','RJ','SP'";
	public static final String REGIAO_CENTRO_OESTE = "'DF','GO','MT','MS'";
	
	//formato de datas
	public static final SimpleDateFormat FORMATADORHIBERNATE = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat FORMATADORORACLE = new SimpleDateFormat("dd/MM/yyyy");
	//Guarda o id de Tipo doenca saudavel
	public static final int SAUDAVEL = 5;
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean isEmailValid(String value) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		if (value == null)
			return false;
		else
			return pattern.matcher(value.toString()).matches();
	}

	public static String formataCpf(String cpf){
		try {
			if ((cpf != null) && (!"".equals(cpf)) && cpf.length() <= 11) {
				MaskFormatter mf;
				mf = new MaskFormatter("###.###.###-##");
				mf.setValueContainsLiteralCharacters(false);
				cpf = mf.valueToString(cpf);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cpf;
	}

	public static String formataCep(String cep){

		if ((cep != null) && (!"".equals(cep)) && cep.length() <= 8) {
			MaskFormatter mf;
			try {
				mf = new MaskFormatter("#####-###");
				mf.setValueContainsLiteralCharacters(false);
				cep = mf.valueToString(cep);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return cep;

	}
	
	public static String formataNumeroProcessoUnico(String numeroProcesso)
			throws Exception {
		if ((numeroProcesso != null)
				&& (!"".equals(numeroProcesso) && (numeroProcesso.length() == 20))) {
			MaskFormatter mf = new MaskFormatter("#######-##.####.#.##.####");
			mf.setValueContainsLiteralCharacters(false);
			numeroProcesso = mf.valueToString(numeroProcesso);
		}
		return numeroProcesso;
	}

	public static String formataNumeroTelefone(String telefone)
			throws Exception {
		MaskFormatter mf = new MaskFormatter("(##)#########");
		mf.setValueContainsLiteralCharacters(false);
		telefone = mf.valueToString(telefone);
		return telefone;
	}

	public static String formataNumeroGuia(String numeroGuia) throws Exception {
		MaskFormatter mf = new MaskFormatter("#####.####");
		mf.setValueContainsLiteralCharacters(false);
		numeroGuia = mf.valueToString(numeroGuia);
		return numeroGuia;
	}

	/**
	 * TODO: Metodo que retorna a quantidade de dias
	 * 
	 * @param date
	 *            Recebe a data
	 * @return Retorna a quantidade de dias
	 * @author hugo.santos
	 */
	public static int quantidadeDias(Date date) {
		Calendar calendarHoje = Calendar.getInstance();
		Calendar calendarDataParamentro = Calendar.getInstance();
		DateTime dataHoje = new DateTime(calendarHoje.getTime());
		calendarDataParamentro.setTime(date);
		DateTime dataParametro = new DateTime(calendarDataParamentro.getTime());
		return Days.daysBetween(dataParametro, dataHoje).getDays();
	}

	public static String retirarMascara(String value) {
		value = value.trim().replaceAll("\\D", "");
		return value;
	}

	/**
	 * TODO: Metodo que calcula a idade de uma pessoa
	 * 
	 * @param dataNasc Recebe a data de nascimento
	 * @param mascaraDataPadrao Recebe a masca da data
	 * @return Retorna a idade da pessoa
	 * @author hugo.santos
	 */
	public static int calculaIdade(Date dataNasc, String mascaraDataPadrao) {
		SimpleDateFormat formatDate = new SimpleDateFormat(mascaraDataPadrao);
		formatDate.format(dataNasc);
		Date dataAtual = new Date();
		formatDate.format(dataAtual);
		int idade = calculaAnoEntreDatas(dataNasc, dataAtual);
		return idade;
	}

	
	/**
	 * TODO: Modifica a data do formato date para String
	 * @param data Recebe um objeto Date
	 * @return Retorn uma da no formato String
	 * @throws ParseException
	 */
	public static String retornaString(Date data) throws ParseException{
		SimpleDateFormat in= new SimpleDateFormat("yyyy-MM-dd");  
		SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");  		  
		String result = out.format(in.parse(data.toString()));
		return result;
	}
	
	/**
	 * TODO: Metodo que retorna o total de meses entre anos
	 * 
	 * @param inicio Recebe a data inicio no formato String exemplo "01/01/2010"
	 * @param fim Recebe a data fim no formato String exempl "01/01/2010"
	 * @param mascaraDataPadrao Recebe a mascara para a data exemplo "dd/MM/yyyy"
	 * @return Retorna como string o valor dos meses entre a data inicio e fim
	 * @throws ParseException
	 */
	public static String calculaMeses(String inicio, String fim,String mascaraDataPadrao) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(mascaraDataPadrao);
		Date dataInicio = format.parse(inicio);
		Date dataFim = format.parse(fim);
		final double MES_EM_MILISEGUNDOS = 30.0 * 24.0 * 60.0 * 60.0 * 1000.0;
		double numeroDeMeses = (double) ((dataFim.getTime() - dataInicio.getTime()) / MES_EM_MILISEGUNDOS);
		String meses = String.valueOf(numeroDeMeses);
		return meses.substring(0, 3).replace(".", "");
	}

	public static int calculaAnoEntreDatas(Date dataMaisAntiga, Date dataMaisNova) {
		Calendar oldDate = new GregorianCalendar();
		oldDate.setTime(dataMaisAntiga);

		Calendar newDate = new GregorianCalendar();
		newDate.setTime(dataMaisNova);

		// Obtém a idade baseado no ano
		int idade = newDate.get(Calendar.YEAR) - oldDate.get(Calendar.YEAR);

		oldDate.add(Calendar.YEAR, idade);

		if (newDate.before(oldDate)) {
			idade--;
		}

		return idade;
	}
	/**
	 * Metodo que valida o CPF
	 * */
	public static boolean validaCPF(String strCpf) {
		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		strCpf = strCpf.replaceAll("\\D", "");
		
		if(strCpf == null || strCpf.length() != 11 || isCPFPadrao(strCpf)){
			return false;
		}
		
		for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount))
					.intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito1 = 0;
		} else {
			digito1 = 11 - resto;
		}

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2) {
			digito2 = 0;
		} else {
			digito2 = 11 - resto;
		}

		// Digito verificador do CPF que está sendo validado.
		String nDigVerific = strCpf.substring(strCpf.length() - 2,
				strCpf.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return nDigVerific.equals(nDigResult);
	}
	
	/**
	 * Metodo que verifica se o CPF e um numero padrao
	 **/
	private static boolean isCPFPadrao(String cpf){		
		if (cpf.equals("00000000000") || cpf.equals("11111111111")
				|| cpf.equals("22222222222") || cpf.equals("33333333333")
				|| cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777")
				|| cpf.equals("88888888888") || cpf.equals("99999999999")) {
			return true;
		}
		return false;
	}

	public static String formataCertidaoNascimentoNova(String numero) {
		if ((numero != null) && (!"".equals(numero) && (numero.length() == 32))) {
			MaskFormatter mf;
			try {
				mf = new MaskFormatter(
						"###### ## ## #### # ##### ### ####### ##");
				mf.setValueContainsLiteralCharacters(false);
				numero = mf.valueToString(numero);
			} catch (ParseException e) {
				return numero;
			}

		}
		return numero;
	}

	public static String getLabelSexo(String sexo) {
		String label = "";
		if (sexo != null) {
			if (sexo.toUpperCase().equals("M")) {
				label = "Masculino";
			} else if (sexo.toUpperCase().equals("F")) {
				label = "Feminino";
			}
		}

		return label;
	}

	public static String md5(String senha) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		return hash.toString(16);
	}
	/**
	 * Metodo que retorna a string formatada
	 * 
	 * @param String texto
	 * @author carlos.lopes
	 * @return texto capitalizado
	 * */
	public static String capitalizar(String texto) {	
		texto = WordUtils.capitalizeFully(texto);
		texto = texto.replaceAll("De ", "de ").replaceAll("Da ", "da ").replaceAll("E ", "e ")
				.replaceAll("Do ", "do ").replaceAll("Dos ", "dos ").replaceAll("Das ", "das ");;		
		return texto;
	}
	/**
	 * Metodo que retorna a string do arquivo de propriedade messages
	 * 
	 * @param String texto
	 * @author carlos.lopes
	 * @return texto do arquivo de propriedade
	 * */
	public static String getPropriedadeMessage(String chave){
		return ResourceBundle.getBundle("messages").getString(chave);		
	}
	
	/**
	 * Metodo que remove os acentos de uma String
	 * 
	 * @author carlos.lopes
	 * @param String
	 * @return String sem acento
	 * */
	public static String removeAcentos(String string) {		 
		 if (string != null){
		        string = Normalizer.normalize(string, Normalizer.Form.NFD);
		        string = string.replaceAll("[^\\p{ASCII}]", "");
		    }
		return string;
	}
	/**
	 * Valida campo Integer
	 * @return boolean
	 * */
	public static boolean validarCampoInteger(BigInteger campo) {

		if (campo != null && !campo.equals(0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * Funcao que valida se o numero processo esta no formato novo ou antigo
     * 
     * @param numProcessoHabilitacao
     * @return boolean
     */
	
    public static boolean validaNumProcessoHabilitacao(String numProcessoHabilitacao) {
	// Padrao novo 0000000-00.0000.0.00.0000
	if (numProcessoHabilitacao.length() == 25) {
	    String numeros = numProcessoHabilitacao.substring(0, 6) + numProcessoHabilitacao.substring(8, 9) + numProcessoHabilitacao.substring(11, 14)
		    + numProcessoHabilitacao.substring(16, 16) + numProcessoHabilitacao.substring(18, 19) + numProcessoHabilitacao.substring(21, 25);
	    String barra = numProcessoHabilitacao.substring(7, 8);
	    String ponto1 = numProcessoHabilitacao.substring(10, 11);
	    String ponto2 = numProcessoHabilitacao.substring(15, 16);
	    String ponto3 = numProcessoHabilitacao.substring(17, 18);
	    String ponto4 = numProcessoHabilitacao.substring(20, 21);

	    if (validarCampoInteger(BigInteger.valueOf(Long.parseLong(numeros.trim())))) {
		if (barra.equals("-") && ponto1.equals(".") && ponto2.equals(".") && ponto3.equals(".") && ponto4.equals(".")) {
		    return true;
		}
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
	return false;

    }
    
//   	/**
//	 * Metodo que faz a validacao dos dados de processo
//	 * 
//	 * @param String numProcesso, TipoProcessoEnum t
//	 * @throws ServiceException
//	 */
//	public static void validarProcesso(String numProcesso, EnumTipoProcesso t) throws ServiceException {
//		String numero = "";
//
//		if (numProcesso == null || numProcesso.isEmpty() || numProcesso.equals("Não informado")) {
//			throw new ServiceException("Informe um número do " + t.getValor()+ ".");
//		} else {
//			numero = retirarMascara(numProcesso);
//			
//			if (numero.length() != 20) {
//				throw new ServiceException("Informe um número de "+ t.getValor()
//						+ " válido, no formato #######-##.####.#.##.####");
//			}
//
//			if (numProcesso.equals("0000000-00.0000.0.00.0000")
//					|| numProcesso.equals("1111111-11.1111.1.11.1111")
//					|| numProcesso.equals("2222222-22.2222.2.22.2222")
//					|| numProcesso.equals("3333333-33.3333.3.33.3333")
//					|| numProcesso.equals("4444444-44.4444.4.44.4444")
//					|| numProcesso.equals("5555555-55.5555.5.55.5555")
//					|| numProcesso.equals("6666666-66.6666.6.66.6666")
//					|| numProcesso.equals("7777777-77.7777.7.77.7777")
//					|| numProcesso.equals("8888888-88.8888.8.88.8888")
//					|| numProcesso.equals("9999999-99.9999.9.99.9999")) {
//
//				throw new ServiceException("Informe o número do "+ t.getValor() + " válido.");
//
//			}
//		}
//	}	
}
