package com.alver.web.presenter;

import com.alver.app.service.Service;
import com.alver.core.model.Entity;
import com.alver.web.app.HtmlPresenter;
import com.alver.web.component.Component;
import com.alver.web.component.ComponentImpl;
import com.alver.web.component.ParentImpl;
import com.alver.web.css.CssDeclarationImpl;
import com.alver.web.html.DivImpl;
import com.alver.web.input.Control;
import com.alver.web.style.StyleAttributeImpl;

import javax.inject.Inject;
import java.util.List;

import static com.alver.web.input.ComplexControl.buildControl;

public class EntityPresenter<T extends Entity> implements Presenter {
	
	private final Service<T> service;
	private final HtmlPresenter htmlPresenter;
	
	@Inject
	public EntityPresenter(
		Service<T> service,
		HtmlPresenter htmlPresenter
	) {
		this.htmlPresenter = htmlPresenter;
		this.service = service;
	}
	
	public String render() {
		Component<?> header = ComponentImpl.builder().template("""
			<h1>Entities</h1>
			""").build();
		
		Component<?> entities = DivImpl.builder()
			.addAttributes(StyleAttributeImpl.of(List.of(
				CssDeclarationImpl.of("margin", "0 20%"),
				CssDeclarationImpl.of("display", "flex"),
				CssDeclarationImpl.of("flex-direction", "column"),
				CssDeclarationImpl.of("justify-content", "center")
			)))
			.children(service.getAll().stream()
				.map(entity -> (Control<?, ?>) buildControl("entity", entity))
				.toList())
			.build();
		
		return htmlPresenter.renderContent(ParentImpl.builder()
			.addChildren(header, entities)
			.build());
	}
}
