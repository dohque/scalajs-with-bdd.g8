
const path = require("path");

const MiniCssExtractPlugin = require("mini-css-extract-plugin");

// Dir at the top of the module.
const rootDir = path.resolve(__dirname, "../../../..");
module.exports.rootDir = rootDir;

// Resources dir on sbt
const resourcesDir = path.resolve(rootDir, "src/main/resources");
module.exports.resourcesDir = resourcesDir;

// Extract css to a file, use only in production
module.exports.extractCSS = ({
  devMode,
  include,
  exclude,
  use = [],
  ci = false
}) => {
  const filename = ci ? "[name].css" : "[name].[contenthash].css";
  const chunkFilename = ci ? "[id].css" : "[id].[contenthash].css";
  // Output extracted CSS to a file
  const plugin = new MiniCssExtractPlugin({
    filename: devMode ? "[name].css" : filename,
    chunkFilename: devMode ? "[id].css" : chunkFilename
  });

  return {
    module: {
      rules: [
        {
          test: /\.less\$|\.css\$/,
          include,
          exclude,
          use: [devMode ? "style-loader" : MiniCssExtractPlugin.loader].concat(
            use
          )
        }
      ]
    },
    plugins: [plugin]
  };
};

// This is needed for scala.js projects
module.exports.resourceModules = {
  resolve: {
    modules: [path.resolve(__dirname, "node_modules"), resourcesDir]
  }
};

// Let webpack find assets on sbt paths
module.exports.resolve = {
  resolve: {
    alias: {
      // Find files on resources
      resources: resourcesDir,
      // Used to find the produced scala.js file
      sjs: __dirname
    }
  }
};

// Loader for fonts
exports.fontAssets = {
  module: {
    rules: [
      {
        // Match woff2 in addition to patterns like .woff?v=1.1.1.
        test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?\$/,
        use: {
          loader: "url-loader",
          options: {
            // Limit at 50k. Above that it emits separate files
            limit: 50000,

            // url-loader sets mimetype if it's passed.
            // Without this it derives it from the file extension
            mimetype: "application/font-woff",

            // Output below fonts directory
            name: "[name].[hash].[ext]"
          }
        }
      }
    ]
  }
};

// Loads assets as files including images, audio and old style fonts
exports.extraAssets = {
  module: {
    rules: [
      {
        test: /\.jpe?g\$|\.gif\$|\.png\$|\.ttf\$|\.eot\$|\.svg\$|.mp3\$/,
        loader: "file-loader",
        options: {
          name: "[name].[hash].[ext]"
        }
      }
    ]
  }
};
