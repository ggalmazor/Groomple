package groomple.test

import java.security.InvalidParameterException

import groomple.Groomple

import org.junit.Test
import static org.junit.Assert.assertThat
import static org.hamcrest.CoreMatchers.*

class GroompleTests {
	
	@Test(expected = InvalidParameterException.class)
	void lanza_excepcion_al_recuperar_una_propiedad_que_no_existe() {
		Groomple g = new Groomple()
		g.foo
	}

	@Test
	void recupera_una_propiedad_usando_accesor() {
		Groomple g = new Groomple()
		g.foo = "bar"
		assertThat g.getProperty("foo"), is("bar")
	}

	@Test
	void recupera_una_propiedad_usando_forma_array() {
		Groomple g = new Groomple()
		g.foo = "bar"
		assertThat g["foo"], is("bar")
	}

	@Test
	void recupera_una_propiedad_usando_atributo() {
		Groomple g = new Groomple()
		g.foo = "bar"
		assertThat g.foo, is("bar")
	}

	@Test
	void asigna_una_propiedad_usando_accesor() {
		Groomple g = new Groomple()
		g.setProperty "foo", "bar"
		assertThat g.foo, is("bar")
	}

	@Test
	void asigna_una_propiedad_usando_forma_array() {
		Groomple g = new Groomple()
		g["foo"] = "bar"
		assertThat g.foo, is("bar")
	}

	@Test
	void asigna_una_propiedad_usando_atributo() {
		Groomple g = new Groomple()
		g.foo = "bar"
		assertThat g.foo, is("bar")
	}

	@Test(expected = InvalidParameterException.class)
	void borra_una_propiedad() {
		Groomple g = new Groomple()
		g.foo = "bar"
		g.remove "foo"
		g.foo
	}

	@Test
	void por_defecto_devuelve_distintas_instancias() {
		Groomple g = new Groomple()
		g.service = { new groomple.test.Service() }
		def foo = g.service
		def bar = g.service
		assertThat foo, is(Service.class)
		assertThat foo, is(not(bar))
	}
	
	@Test
	void se_accede_a_los_servicios_como_metodos_del_contenedor() {
		Groomple g = new Groomple()
		g.service = { new groomple.test.Service() }
		def foo = g.service()
		assertThat foo, is(Service.class)
	}

	@Test
	void share_convierte_el_servicio_en_un_singleton() {
		Groomple g = new Groomple()
		g.share("service") { new groomple.test.Service() }
		def foo = g.service
		def bar = g.service
		assertThat foo, is(Service.class)
		assertThat foo, is(bar)
	}
	
	@Test
	void protect_evita_la_ejecucion_del_servicio_al_recuperarlo() {
		Closure serviceInstantiator = { new groomple.test.Service() }
		Groomple g = new Groomple()
		g.protect "service", serviceInstantiator 
		def foo = g.service
		assertThat foo, is(serviceInstantiator)
	}
	
}
