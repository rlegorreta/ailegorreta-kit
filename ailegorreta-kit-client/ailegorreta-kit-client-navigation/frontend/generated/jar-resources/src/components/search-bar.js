import { html, LitElement } from "lit";
import '@vaadin/icons/vaadin-icons.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/checkbox/src/vaadin-checkbox.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '../../styles/shared-styles.js';

class SearchBar extends LitElement {

  render() {
    return html`
    <style include="shared-styles">
      :host {
        position: relative;
        z-index: 2;
        display: flex;
        flex-direction: column;
        overflow: hidden;
        padding: 0 var(--lumo-space-s);
        background-image: linear-gradient(var(--lumo-shade-20pct), var(--lumo-shade-20pct));
        background-color: var(--lumo-base-color);
        box-shadow: 0 0 16px 2px var(--lumo-shade-20pct);
        order: 1;
        width: 100%;
        height: 48px;
      }

      .row {
        display: flex;
        align-items: center;
        height: 3em;
      }

      .checkbox,
      .clear-btn,
      :host([show-extra-filters]) .action-btn {
        display: none;
      }

      :host([show-extra-filters]) .clear-btn {
        display: block;
      }

      :host([show-checkbox]) .checkbox.mobile {
        display: block;
        transition: all 0.5s;
        height: 0;
      }

      :host([show-checkbox][show-extra-filters]) .checkbox.mobile {
        height: 2em;
      }

      .field {
        flex: 1;
        width: auto;
        padding-right: var(--lumo-space-s);
      }

      @media (min-width: 700px) {
        :host {
          order: 0;
        }

        .row {
          width: 100%;
          max-width: 964px;
          margin: 0 auto;
        }

        .field {
          padding-right: var(--lumo-space-m);
        }

        :host([show-checkbox][show-extra-filters]) .checkbox.desktop {
          display: block;
        }

        :host([show-checkbox][show-extra-filters]) .checkbox.mobile {
          display: none;
        }
      }
    </style>

    <div class="row">
      <vaadin-text-field id="field" class="field" placeholder="B&uacute;squeda" on-focus="_onFieldFocus" 
        on-blur="_onFieldBlur" theme="white" clear-button-visible>
        <vaadin-icon icon="vaadin:search" slot="prefix"></vaadin-icon>
      </vaadin-text-field>
      <vaadin-checkbox id="checkBox" class="checkbox desktop" checked="false" on-focus="_onFieldFocus" 
        on-blur="_onFieldBlur"></vaadin-checkbox>
      <vaadin-button id="clear" class="clear-btn" theme="tertiary">
      </vaadin-button>
      <vaadin-button id="action" class="action-btn" theme="primary">
        <vaadin-icon icon="vaadin:plus" slot="prefix"></vaadin-icon>
      </vaadin-button>
    </div>

    <vaadin-checkbox id="checkbox" class="checkbox mobile" checked="false" on-focus="_onFieldFocus" 
      on-blur="_onFieldBlur"></vaadin-checkbox>
`;
  }

  static get is() {
    return 'search-bar';
  }
  static get properties() {
    return {
      _isSafari: {
        type: Boolean,
        value: function() {
          return /^((?!chrome|android).)*safari/i.test(navigator.userAgent);
        }
      }
    };
  }

  ready() {
    super.ready();
    // In iOS prevent body scrolling to avoid going out of the viewport
    // when keyboard is opened
    this.addEventListener('touchmove', e => e.preventDefault());
  }

  _setShowExtraFilters(checkboxChecked, focused) {
    this._debouncer = Debouncer.debounce(
      this._debouncer,
      // Set 1 millisecond wait to be able move from text field to checkbox with tab.
      timeOut.after(1), () => {
        this.showExtraFilters = checkboxChecked || focused;

        // Safari has an issue with repainting shadow root element styles when a host attribute changes.
        // Need this workaround (toggle any inline css property on and off) until the issue gets fixed.
        // Issue is fixed in Safari 11 Tech Preview version.
        if (this._isSafari && this.root) {
          Array.from(this.root.querySelectorAll('*')).forEach(function(el) {
            el.style['-webkit-backface-visibility'] = 'visible';
            el.style['-webkit-backface-visibility'] = '';
          });
        }
      }
    );
  }

  _onFieldFocus(e) {
    e.target == this.$.field && this.dispatchEvent(new Event('search-focus', {bubbles: true, composed: true}));
    this._focused = true;
  }

  _onFieldBlur(e) {
    e.target == this.$.field && this.dispatchEvent(new Event('search-blur', {bubbles: true, composed: true}));
    this._focused = false;
  }
}

customElements.define(SearchBar.is, SearchBar);
