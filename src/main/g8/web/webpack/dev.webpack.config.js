const path = require("path");
const Webpack = require("webpack");
const Merge = require("webpack-merge");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const parts = require("./webpack.parts");
const ScalaJSConfig = require("./scalajs.webpack.config");

const isDevServer = process.argv.some(s => s.match(/webpack-dev-server\.js\$/));

const Web = Merge(
  ScalaJSConfig,
  parts.resolve,
  parts.resourceModules,
  parts.extractCSS({
    devMode: true,
    use: ["css-loader"]
  }),
  parts.extraAssets,
  parts.fontAssets,
  {
    module: {
      rules: [{
        test: require.resolve('jquery'),
        use: [{
            loader: 'expose-loader',
            options: 'jQuery'
        },{
            loader: 'expose-loader',
            options: '\$'
        }]
      }]
    }
  },
  {
    mode: "development",
    entry: {
      "web": [ path.resolve(parts.resourcesDir, "./dev.js") ]
    },
    output: {
      publicPath: "/" // Required to make the url navigation work
    },
    module: {
      // Don't parse scala.js code. it's just too slow
      noParse: function(content) {
        return content.endsWith("-fastopt");
      }
    },
    plugins: [
      new Webpack.ProvidePlugin({
        \$: 'jquery',
        jQuery: 'jquery',
        'window.jQuery': 'jquery'
      }),
      // Needed to enable HMR
      new Webpack.HotModuleReplacementPlugin(),
      new HtmlWebpackPlugin({
        meta: {
            viewport: 'width=device-width, initial-scale=1.0'
        },
        filename: "index.html",
        title: "$name;format="Camel"$",
        chunks: [ "web" ]
      })
    ]
  }
);

// Enable status bar to display on the page when webpack is reloading
if (isDevServer) {
  Web.entry.web.push("webpack-dev-server-status-bar");
}

module.exports = Web;
