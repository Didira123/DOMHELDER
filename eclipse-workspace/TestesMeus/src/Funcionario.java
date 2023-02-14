public class Funcionario {
	private static Integer cont = 0;
	private static Funcionario fPosOcupada;
	private static Funcionario fPosAnterior;
	private static StringBuilder string;

	private Integer id;
	private String nome;
	private Double salario;

	private Funcionario next;

	public void adiciona(Funcionario f) {
		if (this.next == null) {
			this.next = f;
			this.next.id = cont;
		} else {
			this.next.adiciona(f);
		}
		cont++;
	}

	public Funcionario busca(int pos) {
		if (this.id == pos) {
			return this;
		}
		return this.next.busca(pos);
	}

	private boolean posicaoOcupada(int pos) {
		if (this.id == pos) {
//			fPosOcupada = this;
			return true;
		} else if (this.next != null) {
			fPosAnterior = this;
			return this.next.posicaoOcupada(pos);
		}
		return false;
	}

	public void remove(int pos) {
		if (posicaoOcupada(pos)) {
			if (this.next.id == pos) {
				fPosAnterior = this.next;
				Funcionario f = fPosAnterior;
				for (; f.next != null;) {
					f.next.id = f.id + 1;
					f = f.next;
				}
			}
		} else {
			throw new NullPointerException("Posição inexistente");
		}
		cont--;
	}

	public boolean contem(String nome) {
		if (this.nome.equalsIgnoreCase(nome)) {
			return true;
		} else if (this.next != null) {
			return this.contem(nome);
		} else {
			return false;
		}
	}

	public int tamanho() {
		return cont + 1;
	}

	public void imprimeLista() {
		string.append(this.toString());
		if (this.next != null) {
			this.next.imprimeLista();
		} else {
			System.out.print(string.toString());
			string.replace(0, string.length() - 1, "");
		}
	}

	public void ordenaPorNome() {
		FuncionarioCampos[] list = new FuncionarioCampos[Funcionario.cont];
		Funcionario maior = new Funcionario();
		for(int cont=0;cont<Funcionario.cont;cont++) {
			Funcionario f1 = busca(cont);
			for(int cont2=1;cont2<Funcionario.cont;cont2++) {
				Funcionario f2 = busca(cont);
				if (f1.nome.compareTo(f2.nome)>0) {
					maior = f1;
				} else {
					maior = f2;
				}
			}
			list[cont] = new FuncionarioCampos(maior.id, maior.nome, maior.salario);
		}
		
		for(int cont = 0; cont< Funcionario.cont; cont++) {
			remove(0);
		}
		
		for(int cont = 0; cont< Funcionario.cont; cont++) {
			adiciona(new Funcionario(list[cont].getNome(), list[cont].getSalario()));
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Double getSalario() {
		return this.salario;
	}

	private Funcionario() {
	}

	public Funcionario(String nome, Double salario) {
		setNome(nome);
		setSalario(salario);
		cont++;
	}

}
