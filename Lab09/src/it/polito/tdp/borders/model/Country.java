package it.polito.tdp.borders.model;

public class Country {
	
	

	private int code;
	private String sigla;
	private String nome;
	
	
	
	public Country(int code, String nome, String sigla) {
		super();
		this.code = code;
		this.sigla = sigla;
		this.nome = nome;
	}
	
	
	
	

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (code != other.code)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return code+" "+ nome+ " "+sigla;
	}
	
	
	

}
