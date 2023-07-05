import { html, LitElement } from "lit";
import '@vaadin/icons/vaadin-icons.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '../../styles/shared-styles.js';
class SearchBarCombo extends LitElement {

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

      .combo-box,
      .clear-btn,
      :host([show-extra-filters]) .action-btn {
        display: none;
      }

      :host([show-extra-filters]) .clear-btn {
        display: block;
      }

      :host([show-extra-filters]) .combo-box {
        display: block;
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

        :host([show-extra-filters]) .combo-box {
          display: block;
        }

      }
    </style>

    <div class="row">
      <vaadin-text-field id="field" class="field" placeholder="B&uacute;squeda"
        on-focus="_onFieldFocus" on-blur="_onFieldBlur" theme="white" clear-button-visible>
        <vaadin-icon icon="vaadin:search" slot="prefix"></vaadin-icon>
      </vaadin-text-field>
      <vaadin-combo-box id="combobox" class="combo-box desktop" value="comboboxValue" 
        on-focus="_onFieldFocus" on-blur="_onFieldBlur">
      </vaadin-combo-box>
      <vaadin-button id="clear" class="clear-btn" theme="tertiary">
      </vaadin-button>
      <vaadin-button id="action" class="action-btn" theme="primary">
          <vaadin-icon icon="vaadin:plus" slot="prefix"></vaadin-icon>
      </vaadin-button>
    </div>

`;
    }

    static get is() {
        return 'search-bar-combo';
    }
    static get properties() {
        return {
            buttonIcon: {
                type: String,
                value: 'vaadin:ellipsis-dots-h'
            },
            comboboxValue: {
                type: String,
                notify: true
            },
            clearText: {
                type: String,
                value: 'Limpiar búsqueda'
            },
            showExtraFilters: {
                type: Boolean,
                value: false,
                reflectToAttribute: true
            },
            _isSafari: {
                type: Boolean,
                value: function() {
                    return /^((?!chrome|android).)*safari/i.test(navigator.userAgent);
                }
            }
        };
    }

    static get observers() {
        return [
            '_setShowExtraFilters(fieldValue, comboboxValue, _focused)'
        ];
    }

    ready() {
        super.ready();
        // In iOS prevent body scrolling to avoid going out of the viewport
        // when keyboard is opened
        this.addEventListener('touchmove', e => e.preventDefault());
    }

    _setShowExtraFilters(comboboxValue, focused) {
        this._debouncer = Debouncer.debounce(
            this._debouncer,
            // Set 1 millisecond wait to be able move from text field to combobox with tab.
            timeOut.after(1), () => {
                this.showExtraFilters = comboboxValue || focused;

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

customElements.define(SearchBarCombo.is, SearchBarCombo);
